# W11 動態資料呈現：RecyclerView + Adapter 模式

> **APP 開發課程** ｜ 第 11 週 ｜ 5/14
> **教科書**：Ch07 動態資料呈現
> **單元**：7-1 ListView 與 RecyclerView 對比　/　7-2 RecyclerView 基本實習　/　7-3 ViewHolder 模式
> **AI 介入強度**：🔴 完整介入（W10 起）

---

## 學習目標

1. 理解為什麼 Android 從 ListView 演進到 RecyclerView，以及差異
2. 用 **RecyclerView + Adapter + ViewHolder** 三件套實作可滾動清單
3. 用 **LayoutManager** 切換清單的呈現方式（垂直、水平、Grid）
4. 用 **OnClickListener** 處理清單項目點擊事件
5. 學會用 AI 工具產生 Adapter 樣板，並理解每段程式碼的角色

---

## 一、為什麼是 RecyclerView？

### 1. ListView 的痛點（看一眼就好）

Android 早期的清單元件是 ListView。當資料超過 50 筆時，ListView 會：

| 問題 | 後果 |
|---|---|
| 每捲動一次就 inflate 一個新的 view | 記憶體用量飆升、捲動頓挫 |
| 沒有強制使用 ViewHolder 模式 | 工程師常忘記實作，效能差 |
| 不支援 Grid、橫向、瀑布流 | 換版面要換元件 |

### 2. RecyclerView 的解法

> 「Recycler」就是「回收器」：滾出畫面的 view 不是丟掉，而是丟回池子，給下一筆資料重複使用。

| 解法 | 對應元件 |
|---|---|
| 強制 ViewHolder 模式 | `RecyclerView.ViewHolder` |
| 把「呈現方式」獨立出來 | `LayoutManager`（Linear / Grid / Staggered） |
| 把「資料綁定」獨立出來 | `Adapter` |
| 把「動畫」獨立出來 | `ItemAnimator` |

### 3. 三件套架構圖

```
┌─────────────────────────────────────────┐
│           RecyclerView（容器）           │
├─────────────────────────────────────────┤
│  LayoutManager：決定怎麼排                │
│      ├── LinearLayoutManager（垂直/水平）│
│      ├── GridLayoutManager               │
│      └── StaggeredGridLayoutManager      │
├─────────────────────────────────────────┤
│  Adapter：決定怎麼綁資料                  │
│      ├── onCreateViewHolder（造一個格子） │
│      ├── onBindViewHolder（把資料塞進去） │
│      └── getItemCount（共幾筆）          │
├─────────────────────────────────────────┤
│  ViewHolder：一個格子的快取               │
└─────────────────────────────────────────┘
```

---

## 二、7-1 第一個 RecyclerView 範例

### 步驟 1：在 build.gradle 加入相依套件

打開 `Module: app` 的 `build.gradle`：

```gradle
dependencies {
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
}
```

按 **Sync Now** 等 Gradle 同步完成。

### 步驟 2：在 activity_main.xml 加入 RecyclerView

```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### 步驟 3：設計每一筆資料的版面（item_ocean.xml）

在 `res/layout/` 點右鍵 → New → Layout Resource File，命名 `item_ocean.xml`：

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="12dp">

    <ImageView
        android:id="@+id/imgIcon"
        android:layout_width="48dp"
        android:layout_height="48dp" />

    <TextView
        android:id="@+id/txtName"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:layout_marginStart="12dp"
        android:textSize="18sp" />
</LinearLayout>
```

### 步驟 4：寫 Adapter

新建 `OceanAdapter.java`：

```java
package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OceanAdapter extends RecyclerView.Adapter<OceanAdapter.ViewHolder> {

    private List<String> names;
    private List<Integer> imageIds;

    public OceanAdapter(List<String> names, List<Integer> imageIds) {
        this.names = names;
        this.imageIds = imageIds;
    }

    // ① 一個格子第一次出現時呼叫，造一個 ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ocean, parent, false);
        return new ViewHolder(v);
    }

    // ② 滾到第 position 筆時呼叫，把資料塞進現有的 ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(names.get(position));
        holder.imgIcon.setImageResource(imageIds.get(position));
    }

    // ③ 告訴 RecyclerView 共幾筆
    @Override
    public int getItemCount() {
        return names.size();
    }

    // ViewHolder：一個格子的快取
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
```

### 步驟 5：在 MainActivity 設定 RecyclerView

```java
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.recyclerView);

        // 1. 準備資料
        List<String> names = Arrays.asList(
            "鯨魚", "海豚", "章魚", "海龜", "鯊魚",
            "魟魚", "海星", "水母", "螃蟹", "海馬"
        );
        List<Integer> imgs = Arrays.asList(
            R.mipmap.whale, R.mipmap.dolphin, R.mipmap.octopus, R.mipmap.turtle, R.mipmap.shark,
            R.mipmap.ray, R.mipmap.starfish, R.mipmap.jellyfish, R.mipmap.crab, R.mipmap.seahorse
        );

        // 2. 設定 LayoutManager
        rv.setLayoutManager(new LinearLayoutManager(this));

        // 3. 設定 Adapter
        rv.setAdapter(new OceanAdapter(names, imgs));
    }
}
```

執行模擬器，你應該會看到一個可以順暢滾動的海洋生物清單。

> 🌊 **海洋主題**：本週清單繼續用海洋生物，銜接期末專題。圖檔可沿用 W10 的 mipmap 圖。

---

## 三、7-2 切換不同的 LayoutManager

只需改一行，就能切換清單呈現方式：

```java
// 垂直清單（預設）
rv.setLayoutManager(new LinearLayoutManager(this));

// 水平清單
rv.setLayoutManager(
    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
);

// 2 欄 Grid
rv.setLayoutManager(new GridLayoutManager(this, 2));

// 3 欄 Grid
rv.setLayoutManager(new GridLayoutManager(this, 3));
```

| LayoutManager | 適用情境 |
|---|---|
| LinearLayoutManager | 一般清單、訊息列表 |
| GridLayoutManager | 商品圖片、相簿縮圖 |
| StaggeredGridLayoutManager | 瀑布流、Pinterest 風格 |

---

## 四、7-3 處理點擊事件

讓使用者點清單項目跳到下一頁：

```java
// 在 ViewHolder 內或 onBindViewHolder 內加：
holder.itemView.setOnClickListener(v -> {
    String name = names.get(holder.getAdapterPosition());
    Toast.makeText(v.getContext(), "你點了：" + name, Toast.LENGTH_SHORT).show();

    // 跳到下一頁
    Intent intent = new Intent(v.getContext(), DetailActivity.class);
    intent.putExtra("name", name);
    v.getContext().startActivity(intent);
});
```

---

## 五、本週重點觀念複習卡

| 觀念 | 一句話記憶 |
|---|---|
| RecyclerView 為什麼比 ListView 好 | 強制 ViewHolder + 解耦 LayoutManager + 高效能回收 |
| Adapter 三大方法 | onCreateViewHolder（造）、onBindViewHolder（綁）、getItemCount（數） |
| ViewHolder 的角色 | 把 findViewById 結果快取，避免每次滾動都重新查 |
| 改變清單方向 | 換 LayoutManager 一行就行，不用改 Adapter |
| 點擊事件放哪裡 | 通常放在 onBindViewHolder 裡，用 itemView.setOnClickListener |

---

## 六、AI 輔助開發練習（W10 起完整介入）

### 6-1 AI 產 Adapter 樣板

對 ChatGPT / Claude / Copilot 下這個 prompt：

> 請幫我寫一個 Android RecyclerView 的 Adapter，資料是 List<String>，每個 item 顯示一段文字。請用 Java（不要用 Kotlin），並逐行解釋你寫的程式碼。

### 6-2 「先理解再使用」流程（AI-RED）

這是本班特別強調的流程。每次用 AI 產生程式碼時：

| 階段 | 做什麼 |
|---|---|
| **R**ead | 把 AI 給的程式碼**全部讀過**一遍，不懂的字詞先標起來 |
| **E**xplain | 用自己的話對著程式碼**解釋**：這段在做什麼？為什麼要這樣寫？ |
| **D**ebug | 試跑、改一個小變數，看會發生什麼，**確認自己理解** |
| 然後才能用 | 確認三步都做了，才把這段程式碼放進專案 |

### 6-3 本週 AI 互動紀錄要求

請在你 fork 的 `week11/` 內建立 `AI-RED-LOG.md`，記錄至少一次完整 R-E-D 流程：

```markdown
## 互動 1：產 Adapter 樣板

### 我問 AI 什麼
（貼上你的 prompt）

### AI 回什麼
（貼上 AI 的關鍵回答，不要全貼）

### Read 階段
（你看不懂的地方？）

### Explain 階段
（你用自己的話解釋這段程式碼）

### Debug 階段
（你改了什麼來驗證？發生什麼？）

### 反思
（這次互動學到什麼？AI 哪裡幫到你？哪裡誤導你？）
```

---

## 七、本週作業

> 繳交方式：在你 fork 的 `114-2_APPDEV/week11/` 建立**一個 Android Studio 專案**完成下列功能，push 到你的 fork（沿用學期既有 PR）

### 任務：海洋生物圖鑑 RecyclerView

設計一個「海洋生物圖鑑」App，至少有 8 種海洋生物，每筆顯示**圖片 + 名稱**，點擊後 Toast 提示。

#### 功能規格

| 編號 | 要求 | 配分 |
|---|---|---|
| 1 | RecyclerView 可順暢滾動（≥ 8 筆資料） | 25 |
| 2 | 自訂 item 版面（圖片 + 文字）| 25 |
| 3 | Adapter 三大方法都有實作 | 20 |
| 4 | 點擊清單項目能 Toast 顯示生物名 | 15 |
| 5 | 至少切換一次 LayoutManager（提交兩版截圖：Linear + Grid） | 15 |

#### 繳交清單

| # | 內容 |
|---|---|
| 1 | Android Studio 專案資料夾（含 `app/`、`build.gradle` 等）|
| 2 | 8 張海洋生物圖檔（沿用 W10 或新增）|
| 3 | 兩張模擬器截圖：LinearLayoutManager 與 GridLayoutManager 各一張 |
| 4 | `README.md`：簡述你的設計思路 |
| 5 | `AI-RED-LOG.md`：至少一次完整 Read-Explain-Debug 紀錄 |

#### 繳交期限

W12 上課前（5/20）

---

## 八、給卡住的同學

| 卡點 | 解法 |
|---|---|
| Sync gradle 失敗 | 檢查 `dependencies` 區塊括號、版本號是否寫錯 |
| RecyclerView **找不到類別** | `import androidx.recyclerview.widget.RecyclerView;`，不要用舊的 `android.support.v7` |
| 清單**只顯示一筆**還重疊 | item layout 的 `layout_height` 寫成 `match_parent` 了，改成 `wrap_content` |
| 點擊無反應 | 檢查是不是把 `OnClickListener` 綁在錯的 view 上 |
| `R.mipmap.xxx` **紅字** | 圖檔名違規（大寫、空格、數字開頭），改全小寫英文 |
| Adapter 改了資料**畫面沒變** | 改完資料要呼叫 `adapter.notifyDataSetChanged()` |

---

## 九、下週預告（W12）

W12 進入 **Fragment 與導覽列**，以及 **Room Database 入門**，並進行**小考 3**。本週的 RecyclerView 是 Fragment 內常見的元件，請務必先把本週練熟。
