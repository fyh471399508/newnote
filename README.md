# 期中实验扩展
## 基础功能展示
### 1.笔记主界面列表显示各类的笔记综合


![](https://github.com/fyh471399508/newnote/blob/master/images/3.png)

### 2.笔记详情界面，点击笔记跳转详情界面，可以对内容进行修改或者删除

![](https://github.com/fyh471399508/newnote/blob/master/images/4.png)


### 3.编写笔记界面，可以编写笔记同时保存，同时点击选择标签

![](https://github.com/fyh471399508/newnote/blob/master/images/2.png)

### 4.笔记查询界面，通过输入笔记的标题进行笔记查询，查询成功跳转详情，不成功Toast提示

![](https://github.com/fyh471399508/newnote/blob/master/images/8.png)


## 扩展功能展示
### 1.界面美化，通过使用各种形状变化、颜色搭配、悬浮实现、布局搭配实现界面美化

![](https://github.com/fyh471399508/newnote/blob/master/images/3.png)

### 2.设置悬浮设置界面，来跳转各个功能模块

![](https://github.com/fyh471399508/newnote/blob/master/images/5.png)

### 3.笔记编写背景颜色变换

![](https://github.com/fyh471399508/newnote/blob/master/images/7.png)

![](https://github.com/fyh471399508/newnote/blob/master/images/6.png)

### 4.笔记分类，通过上部导航栏实现笔记分类

![](https://github.com/fyh471399508/newnote/blob/master/images/10.png)

![](https://github.com/fyh471399508/newnote/blob/master/images/11.png)

![](https://github.com/fyh471399508/newnote/blob/master/images/12.png)

![](https://github.com/fyh471399508/newnote/blob/master/images/15.png)


### 5.语音查询笔记，通过语音录入笔记的题目，点击查询

![](https://github.com/fyh471399508/newnote/blob/master/images/13.png)

![](https://github.com/fyh471399508/newnote/blob/master/images/14.png)


## 主要代码展示

### 1.主界面代码


public class MainActivity extends Activity implements View.OnClickListener{

    protected RecyclerView mRecyclerView;
    private List<Node> listNode=new ArrayList<Node>();
    private SQLiteDatabase db;

    private FloatingActionButton fab_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        mRecyclerView = findViewById(R.id.recycleview_like);
        fab_setting=findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);



        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0,0));
        final NodeAdapter nodeAdapter = new NodeAdapter(this, listNode);
        mRecyclerView.setAdapter(nodeAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("id", listNode.get(position).getNodeID());
                bundle.putString("title",listNode.get(position).getNodeTitle());
                bundle.putString("content",listNode.get(position).getNodeContent());
                bundle.putInt("type",listNode.get(position).getNodeClass());
                bundle.putString("date",listNode.get(position).getSaveDate());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public void onScroll(View view, int position) {

            }
        }));
    }


 
    @Override
    protected void onResume() {
        super.onResume();
        this.db = DataBaseUtil.returnDataBase(this);
        Cursor cursor = db.rawQuery("select * from nodepad order by _id desc", null);
        if (cursor.moveToFirst()) {
            do{
                Node node=new Node();
                node.setNodeID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
                node.setNodeTitle(cursor.getString(cursor.getColumnIndex("title")));
                node.setNodeContent(cursor.getString(cursor.getColumnIndex("content")));
                node.setSaveDate(cursor.getString(cursor.getColumnIndex("date")));
                node.setNodeClass(Integer.parseInt(cursor.getString(cursor.getColumnIndex("type"))));
                listNode.add(node);
            }while(cursor.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}

### 2.笔记适配器

public class NodeAdapter extends RecyclerView.Adapter<NodeViewholder> {

    protected Context context;
    protected List<Node> nodeList;
    protected Map nodeViewholderMap = new HashMap<>();

    public NodeAdapter(Context context,List<Node> nodes){
        this.context=context;
        this.nodeList = nodes;
    }

    @NonNull
    @Override
    public NodeViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NodeViewholder(LayoutInflater.from(context).inflate(R.layout.layout_item,null));
    }
    public Map getNodeViewholderMap(){
        return nodeViewholderMap;
    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewholder likeViewholder, int i) {
        Node node=nodeList.get(i);
        switch (node.getNodeClass()){
            case 1:
                likeViewholder.nodeClass.setText("通知");
                likeViewholder.nodeClass.setBackgroundResource(R.drawable.noticebackground);break;
            case 2:
                likeViewholder.nodeClass.setText("日记");
                likeViewholder.nodeClass.setBackgroundResource(R.drawable.diarybackground);break;
            case 3:
                likeViewholder.nodeClass.setText("博客");
                likeViewholder.nodeClass.setBackgroundResource(R.drawable.blogbackground);break;
            case 4:
                likeViewholder.nodeClass.setText("工作");
                likeViewholder.nodeClass.setBackgroundResource(R.drawable.jobbackground);break;
        }
        likeViewholder.nodeTitle.setText(node.getNodeTitle());
        likeViewholder.nodeContent.setText(node.getNodeContent());
        likeViewholder.nodeDate.setText(node.getSaveDate());
    }

    @Override
    public int getItemCount() {
        return null!=nodeList?nodeList.size():0;
    }
}

### 3.分类适配器

public class TypeAdapter extends RecyclerView.Adapter<TypeViewholder>{
    protected Context context;
    protected List<String> types;
    private Map typeViewholderMap=new HashMap<>();
    public TypeAdapter(Context context, List<String> types){
        this.context=context;
        this.types=types;
    }

    @NonNull
    @Override
    public TypeViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TypeViewholder(LayoutInflater.from(context).inflate(R.layout.layout_type_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewholder typeViewholder, int i) {
        String type=types.get(i);
        typeViewholder.mTypeText.setText(type);
        typeViewholderMap.put(i,typeViewholder);
    }

    @Override
    public int getItemCount() {
        return null!=types?types.size():0;
    }
}

### 4.数据库编写

public class DataBaseUtil {


    public static SQLiteDatabase returnDataBase(Context context){
        String path="nodepad.db";

        SQLiteOpenHelper helper=new SQLiteOpenHelper(context,path,null,1) {
            @Override
            public void onCreate(SQLiteDatabase db) {

                //创建
                String sql = "create table nodepad(_id integer primary key autoincrement," + "title varchar(50),"+"content varchar(500),"+"date varchar(50),"+"type integer)";
                db.execSQL(sql);
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //升级
            }
        };
        return helper.getReadableDatabase();

    }
}

### 5.分类界面编写

public class NodeTypeActivity extends AppCompatActivity implements View.OnClickListener {

    
    private TabLayout mTabLayout;
   
    private ViewPager mViewPager;
    
    private List<String> mTitle;
    
    private List<Fragment> mFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_node_type);


        initData();
        initView();

    }


    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("通知");
        mTitle.add("日记");
        mTitle.add("博客");
        mTitle.add("工作");

        mFragment = new ArrayList<>();
        mFragment.add(new NoticeFragment());
        mFragment.add(new DiaryFragment());
        mFragment.add(new BlogFragment());
        mFragment.add(new JobFragment());
    }

    //初始化View
    private void initView() {

        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "position:" + position);
                if (position == 0) {
                
                } else {
                 
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
           
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

          
            @Override
            public int getCount() {
                return mFragment.size();
            }

            
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}


### 6.语音界面编写

public class VoiceActivity extends Activity implements View.OnClickListener {

    private LinearLayout click;
    private LinearLayout find;

    private EditText mResultText;

    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_voice);
        click = (LinearLayout) findViewById(R.id.voice_click);
        mResultText = ((EditText) findViewById(R.id.voicce_edit));
        find = findViewById(R.id.voice_click_find);
        this.db = DataBaseUtil.returnDataBase(this);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql= "select * from nodepad where title="+"'"+mResultText.getText().toString()+"'" ;
                Cursor cursor = db.rawQuery(sql, null);
                cursor.moveToFirst();
                if(cursor.getCount()!=0){
                    Intent intent=new Intent(VoiceActivity.this,DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
                    bundle.putString("title",cursor.getString(cursor.getColumnIndex("title")));
                    bundle.putString("content",cursor.getString(cursor.getColumnIndex("content")));
                    bundle.putInt("type",Integer.parseInt(cursor.getString(cursor.getColumnIndex("type"))));
                    bundle.putString("date",cursor.getString(cursor.getColumnIndex("date")));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(VoiceActivity.this, "查询笔记题目为空,请从新输入", Toast.LENGTH_LONG).show();
                    mResultText.setText("");
                }
            }
        });
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5d2945cd");

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnVoice();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void btnVoice() {
        RecognizerDialog dialog = new RecognizerDialog(VoiceActivity.this,null);

        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
        Toast.makeText(this, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    
    private void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());
        
        mResultText.append(text);
    }

    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
}


### 7.工具类编写

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int leftRight;
    private int topBottom;

    public SpaceItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        //竖直方向的
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
           
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.bottom = topBottom;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
            outRect.right = leftRight;
        } else {
           
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.right = leftRight;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
            outRect.bottom = topBottom;
        }
    }
}

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;
    private View childView;
    private RecyclerView touchView;

    public RecyclerItemClickListener(Context context, RecyclerItemClickListener.OnItemClickListener listener){
        mListener = listener;
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, touchView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (childView != null && mListener != null) {
                    mListener.onLongClick(childView, touchView.getChildAdapterPosition(childView));
                }
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (childView != null && mListener != null) {
                    mListener.onScroll(childView, touchView.getChildAdapterPosition(childView));
                }
                return true;
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);

        void onScroll(View view, int position);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        touchView = rv;
        childView = rv.findChildViewUnder(e.getX(), e.getY());
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

}


