package com.anphoenix.data


import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor,HColumnDescriptor}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat

import org.apache.spark._
import org.apache.spark.rdd.NewHadoopRDD

import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.hadoop.mapred.JobConf

import java.nio.ByteBuffer
import org.apache.spark.graphx.Graph
import scala.reflect._
import org.apache.hadoop.hbase.util.Bytes;

object HBaseUtils{

 var cf:String = ""

	def convertMap(map:Map[String,String]) = {
           val p = new Put(Bytes.toBytes(map.get("id").get))
	   map.keys.map{key=>if (!key.equals("id"))p.add(Bytes.toBytes(this.cf), Bytes.toBytes(key),Bytes.toBytes(map.get(key).get))}
           (new ImmutableBytesWritable, p)
        }


 def saveAsHBase(rdd:RDD[Map[String,String]], tableName:String, cfName:String, hbaseMaster:String, port:String):Unit= {
        var conf = HBaseConfiguration.create()
        conf.set("hbase.zookeeper.property.clientPort", port)
        conf.set("hbase.zookeeper.quorum", hbaseMaster)

        val admin = new HBaseAdmin(conf)
        if(!admin.isTableAvailable(tableName)) {
          val tableDesc = new HTableDescriptor(tableName)
          var hcd:HColumnDescriptor = new HColumnDescriptor(cfName);
          tableDesc.addFamily(hcd)
          admin.createTable(tableDesc)
        }

        val jobConfig: JobConf = new JobConf(conf)
        jobConfig.setOutputFormat(classOf[TableOutputFormat])
        jobConfig.set(TableOutputFormat.OUTPUT_TABLE, tableName)
	this.cf = cfName
        var mappedRdd=rdd.map(convertMap)
        new PairRDDFunctions(mappedRdd).saveAsHadoopDataset(jobConfig)
  }


 
  def readFromHBase(tableName:String, cfName:String, sc:SparkContext, hbaseMaster:String, port:String)={
	var conf = HBaseConfiguration.create()
	conf.set(TableInputFormat.INPUT_TABLE, tableName)
	conf.set(TableInputFormat.SCAN_COLUMN_FAMILY, cfName)
	conf.set("hbase.zookeeper.property.clientPort", port)
	conf.set("hbase.zookeeper.quorum", hbaseMaster)

        val rdde = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
           classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
           classOf[org.apache.hadoop.hbase.client.Result])


	var ret=rdde.map{rdde=>
             var q1=new String(rdde._2.listCells.get(0).getQualifier)
             var rid=new String(rdde._2.getRow)
	     var cells = rdde._2.listCells
	     var map = Map[String,String]()
	     map += "id"->rid
	     for(i <- 1 to cells.size()){
             	var v=new String(cells.get(i-1).getValue)
                var q=new String(cells.get(i-1).getQualifier)
		map += q->v
	     }
	    map
}
	ret

	
  }
}
