# 犬種分類アプリ (Dog Breed Classifier App)

## 概要
犬の写真をアップロードすると、AIが犬種を自動判定する Android アプリです。TensorFlow Lite を使用した機械学習モデルにより、120種類の犬種を分類できます。
## 対応犬種
120種類の犬種に対応（具体的な犬種リストは `labels_ja.txt` を参照）

※ しば犬は対応してません。


## 主な機能
- 📸 **画像選択**: ギャラリーから犬の写真を選択
- 🔍 **犬種推論**: TensorFlow Lite モデルによる犬種分類
- 📊 **結果表示**: 予測された犬種をテキストで表示
- 🇯🇵 **日本語対応**: 犬種名の日本語表示

## スクリーンショット
アプリの使用手順：
1. 「画像を選ぶ」ボタンをタップ
2. ギャラリーから犬の写真を選択
3. 「犬種を推論」ボタンをタップ
4. 予測結果が表示される

## 技術スタック

### 開発環境
- **言語**: Kotlin
- **フレームワーク**: Jetpack Compose
- **UI**: Material3 Design
- **最小SDK**: Android 33

### 機械学習
- **機械学習フレームワーク**: TensorFlow Lite 2.12.0
- **モデル形式**: .tflite
- **入力サイズ**: 224x224 pixels
- **出力クラス数**: 120種類の犬種
- **推論エンジン**: TensorFlow Lite Interpreter

### 主要ライブラリ

#### TensorFlow Lite - Android端末でAI推論
```gradle
implementation 'org.tensorflow:tensorflow-lite:2.12.0'
```
**このアプリの最大の特徴**: Android端末上で直接AI推論を実行

- **エッジAI**: サーバーに依存せず、端末内で完結する機械学習推論
- **高速処理**: ネットワーク通信不要で瞬時に犬種を判定
- **オフライン対応**: インターネット接続不要で動作
- **軽量モデル**: モバイル端末に最適化された.tfliteファイル形式

TensorFlow Liteにより、従来はクラウドサーバーでしか実行できなかった画像認識AIが、Android端末で実現可能

#### Jetpack Compose
```gradle
implementation platform('androidx.compose:compose-bom:2025.06.01')
implementation 'androidx.compose.ui:ui'
implementation 'androidx.compose.ui:ui-graphics'
implementation 'androidx.compose.ui:ui-tooling-preview'
implementation 'androidx.compose.material3:material3'
implementation 'androidx.activity:activity-compose:1.10.1'
```

### Android端末でのAI推論の技術的優位性

1. **リアルタイム推論**: ネットワーク遅延なしで即座に結果表示
2. **データプライバシー**: 画像データが端末外に送信されない
3. **コスト効率**: サーバーレスでランニングコスト不要
4. **可用性**: ネットワーク環境に依存しない安定動作
5. **スケーラビリティ**: ユーザー数増加に伴うサーバー負荷なし

### 推論処理の流れ
1. **画像前処理**: 選択された画像を224x224にリサイズ
2. **テンソル変換**: RGB値を0-1の範囲に正規化
3. **モデル推論**: TensorFlow Lite Interpreterで推論実行
4. **結果処理**: 最高スコアのインデックスから犬種名を取得

## モデル情報
モデルはHugging Faceにあった学習済みモデルを採用
https://huggingface.co/spaces/realfreko/dog_breed/tree/main
- **モデル**: `converted_dog_model.tflite` + `labels_ja.txt`

## 開発・ビルド方法

### 必要条件
- Android Studio Iguana (2023.2.1) 以降
- JDK 11 以降
- Android SDK 35

2. Android Studio でプロジェクトを開く

3. 必要な依存関係をダウンロード
```bash
./gradlew build
```

4. デバイスまたはエミュレーターで実行
```bash
./gradlew installDebug
```

## 使用方法
1. アプリを起動
2. 「画像を選ぶ」ボタンをタップ
3. ギャラリーから犬の写真を選択
4. 画像が表示されたら「犬種を推論」ボタンをタップ
5. 予測結果が表示されます

## 注意事項
- 推論精度は画像の品質や角度によって変動します
- 120種類以外の犬種は正確に分類されない可能性があります
- ミックス犬の場合は、特徴が強い犬種が判定される傾向があります

---
**バージョン**: 1.0.0 

**更新日**: 2025年7月 