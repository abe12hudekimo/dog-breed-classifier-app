package com.example.dogbreedclassifierapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import androidx.core.graphics.scale
import androidx.core.graphics.get
import org.json.JSONObject

fun runInference(bitmap: Bitmap, context: Context): String {
    // モデル読み込み
    val model = Interpreter(loadModelFile(context, "converted_dog_model.tflite"))
    // 入力画像をリサイズ
    val resized = bitmap.scale(224, 224)
    val input = convertBitmapToTensor(resized)

    // 推論実行
    val output = Array(1) { FloatArray(120) }  // 犬種の数に応じて変更
    model.run(input, output)

    // 最も高いスコアのインデックスを取得
    val index = output[0].indices.maxByOrNull { output[0][it] } ?: -1

    // labels.json を読み込み、JSONとしてパース
    val labels = context.assets.open("labels_ja.txt").bufferedReader().readLines()
    val predictedBreed = labels.getOrNull(index) ?: "不明"
    // インデックスを文字列化してラベルを取得
    return "予測された犬種：$predictedBreed"
}

fun runInference2(bitmap: Bitmap, context: Context): String {
    // モデル読み込み
    val model = Interpreter(loadModelFile(context, "Model.tflite"))
    // 入力画像をリサイズ
    val resized = bitmap.scale(224, 224)
    val input = convertBitmapToTensor(resized)

    // 推論実行
    val output = Array(1) { FloatArray(120) }  // 犬種の数に応じて変更
    model.run(input, output)

    // 最も高いスコアのインデックスを取得
    val index = output[0].indices.maxByOrNull { output[0][it] } ?: -1

    // labels.json を読み込み、JSONとしてパース
    val labelJson = context.assets.open("labels.json").bufferedReader().use { it.readText() }
    val labels = JSONObject(labelJson)
    val breedName = labels.optString(index.toString(), "不明")
    // インデックスを文字列化してラベルを取得
    return "予測された犬種：$breedName"
}


fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
    val fileDescriptor = context.assets.openFd(modelName)
    val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
    val fileChannel = inputStream.channel
    return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
}

fun convertBitmapToTensor(bitmap: Bitmap): Array<Array<Array<FloatArray>>> {
    val input = Array(1) { Array(224) { Array(224) { FloatArray(3) } } }
    for (y in 0 until 224) {
        for (x in 0 until 224) {
            val pixel = bitmap[x, y]
            input[0][y][x][0] = Color.red(pixel) / 255f
            input[0][y][x][1] = Color.green(pixel) / 255f
            input[0][y][x][2] = Color.blue(pixel) / 255f
        }
    }
    return input
}
