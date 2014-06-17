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
import org.apache.spark.graphx._
import org.apache.spark.rdd._
import org.apache.hadoop.mapred.JobConf

import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import scala.reflect.{classTag, ClassTag}
import scala.reflect.runtime.universe._

object HbaseTest{


  def main(args: Array[String]) {
	val sc = new SparkContext("local", "HbaseTest",
        System.getenv("SPARK_HOME"),List("target/scala-2.10/hbase-test-project_2.10-1.0.jar"))
	var tableName="Person"
/*
	var userrdd=sc.textFile("hdfs://master/userprofile.txt").map{line=>
	var ss=line.split("\t")
	var nf=ss.length
	if (nf<13){
		var line2=line+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"+" \t"
		ss=line2.split("\t")
	}
	new Person(ss(0),ss(1),ss(2),ss(3),ss(4),ss(5),ss(6),ss(7),ss(8),ss(9),ss(10),ss(11),ss(12))
	}

        println("count: " + userrdd.count)
	HBaseUtils.saveAsHBase(userrdd,tableName)

	userrdd.take(10).foreach(u=>println(u.id + " * " + u.name + " * " + u.city + " * " + u.prov + " * " + u.bokeUrl))


	var added=sc.textFile("hdfs://master/user_topic5k").map{line=>
		var id=line.split(" ")(1)
		var interest=line.split(":")(1)
		(id,interest)
	}
	
*/
        var userinfo=HBaseUtils.readFromHBase(tableName,"a",sc, "master","2222")
println("snow: " + userinfo.first)

var m=userinfo.first
m += "degree"-> "985"
var mli = List(m)
var one=sc.parallelize(mli)
HBaseUtils.saveAsHBase(one,tableName,"a","master","2222")

	sc.stop()
  }
}
