name := "Hbase Test Project"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.0.0"

libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.0.0"

resolvers += "Apache HBase" at "https://repository.apache.org/content/repositories/releases"

libraryDependencies += "org.apache.hbase" % "hbase-server" % "0.98.2-hadoop2"

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.2.0"

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.2.0"

libraryDependencies += "org.apache.hbase" % "hbase-client" % "0.98.2-hadoop2"

libraryDependencies += "org.apache.hbase" % "hbase-common" % "0.98.2-hadoop2"

libraryDependencies += "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.2.0"

libraryDependencies += "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13"

libraryDependencies += "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

resolvers += "Thrift" at "http://people.apache.org/~rawson/repo/"
