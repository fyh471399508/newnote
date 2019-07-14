package com.example.node;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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

    //回调结果：
    private void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());
        // 自动填写地址
        mResultText.append(text);
    }

    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
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
