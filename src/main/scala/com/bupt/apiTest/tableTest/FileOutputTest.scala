package com.bupt.apiTest.tableTest

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.DataTypes
import org.apache.flink.table.api.scala._
import org.apache.flink.table.descriptors.{Csv, FileSystem, Schema}

/**
 * @author yangkun
 * @date 2020/12/21 15:17
 * @version 1.0
 */
object FileOutputTest {
  def main(args: Array[String]): Unit = {
    //1. 表执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val tableEnv = StreamTableEnvironment.create(env)

    //2 从文件读取数据
    tableEnv.connect(new FileSystem().path("input/sensor.txt"))
      .withFormat(new Csv())
      .withSchema(new Schema()
        .field("id", DataTypes.STRING())
        .field("timeStamp", DataTypes.BIGINT())
        .field("temp", DataTypes.DOUBLE())
      )
      .createTemporaryTable("inputTable")
    val sensorTable = tableEnv.from("inputTable")
    //3 转化
    //3.1 简单转化
    val resultTable = sensorTable.select('id,'temp)
        .filter('id ==="sensor_1")
//    resultTable.toAppendStream[(String,Double)].print("res")
    //3.2 聚合转化

    env.execute("table api test")
  }

}
