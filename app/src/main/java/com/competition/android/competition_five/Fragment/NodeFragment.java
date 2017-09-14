package com.competition.android.competition_five.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.util.StaticParam;
import com.competition.android.competition_five.Entity.OcrList;
import com.competition.android.competition_five.UseActivity.NodeContextActivity;
import com.competition.android.competition_five.Ocr.FileUtil;
import com.competition.android.competition_five.Ocr.IDCardActivity;
import com.competition.android.competition_five.Adapter.OcrRecycViewAdapter;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Ocr.RecognizeService;
import com.competition.android.competition_five.Ocr.Test;
import com.competition.android.competition_five.Uilt.L;
import com.competition.android.competition_five.Uilt.OpenUile;
import com.competition.android.competition_five.Uilt.StaticUilt;
import com.competition.android.competition_five.activity.BarrageActivity;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/8/18.
 */

public class NodeFragment extends Fragment {

    private static final int REQUEST_CODE_GENERAL = 105;
    private static final int REQUEST_CODE_GENERAL_BASIC = 106;
    private static final int REQUEST_CODE_GENERAL_ENHANCED = 107;
    private static final int REQUEST_CODE_GENERAL_WEBIMAGE = 108;
    private static final int REQUEST_CODE_BANKCARD = 110;
    private static final String AK = StaticUilt.AK;
    private static final String SK = StaticUilt.SK;

    private boolean hasGotToken = false;

    private AlertDialog.Builder alertDialog;
    private EditText orc_content;
    private EditText ocr_title;

    private  View dialogview;
    private AlertDialog.Builder dialog;

    //得到的结果
    private StringBuilder content = new StringBuilder();

    private LayoutInflater mInflater;


    public static OcrList user_0crList = new OcrList();
    //spinner
    private ArrayAdapter <String> spinner_adapter;
    private List <String> spinner_list = new ArrayList<>();
    private List <OcrList> mOcrLists = new ArrayList<>();
    private Spinner spinner_ocr_list;
    private EditText spinner_add_item;
    private Button finish_button;
    private Button spinner_add_button;
    MaterialEditText materialEditText;
    private String spinnertext = null;
    private  OcrList ocrlist;

    private RecyclerView ocr_list_recyclerview;
    private OcrRecycViewAdapter ocrRecycViewAdapter;
    private static NodeFragment nodeFragment = new NodeFragment();
    public static Fragment newInstance(){

        return nodeFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_node_layout,container,false);
        mInflater = LayoutInflater.from(getActivity());
        alertDialog = new AlertDialog.Builder(getActivity());
        orc_content =  (EditText)view.findViewById(R.id.ocr_content);
        dialog = new AlertDialog.Builder(getActivity());
       ocr_list_recyclerview = (RecyclerView) view.findViewById(R.id.orc_list_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ocr_list_recyclerview.setLayoutManager(linearLayoutManager);
        if(mOcrLists.size()==0)
        {
            initList();
        }
        ocrRecycViewAdapter = new OcrRecycViewAdapter(getActivity(),
                mOcrLists);
        ocrRecycViewAdapter.setOnItemClickLitsener(new OcrRecycViewAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View view, int position,OcrList ocrList) {
               Intent intent = new Intent(getActivity(), NodeContextActivity.class);
                user_0crList = ocrList;
                startActivity(intent);

            }
        });
        ocr_list_recyclerview.setAdapter(ocrRecycViewAdapter);

        initDlalogView();


        //spinner

        OpenUile.setPrimaryDark(getActivity(),R.color.mediumslateblue);
        view.findViewById(R.id.general_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                L.d("触发点击");
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getActivity().getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL);
            }
        });

        view.findViewById(R.id.general_basic_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getActivity().getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
            }
        });

        view.findViewById(R.id.general_enhance_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getActivity().getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_ENHANCED);
            }
        });

        view.findViewById(R.id.general_webimage_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getActivity().getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_WEBIMAGE);
            }
        });

        view.findViewById(R.id.idcard_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), IDCardActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.bankcard_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getActivity().getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);
            }
        });


        /**
         * G,button.setOnclick
         * 弹幕按钮点击事件
         */
        view.findViewById(R.id.barrage_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StaticParam.BITMAP_PHOTO == null) {
                    Toast.makeText(getContext(), "请先去拍摄图片!", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(getContext(), BarrageActivity.class);

                    startActivity(intent);
                }
            }
        });

        // 请选择您的初始化方式
        // initAccessToken();
        initAccessTokenWithAkSk();


        return view;
    }

    private void initList() {
      /*  ArrayList<OcrList> arry = new ArrayList<>();
        ArrayList<String> arrys = new ArrayList<>();*/
            AVQuery<OcrList> query = AVQuery.getQuery(OcrList.class);
        L.d(AVUser.getCurrentUser().getObjectId());
                               query.whereEqualTo("userId",AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId()));
                               query.findInBackground(new FindCallback<OcrList>() {
                                   @Override
                                   public void done(List<OcrList> list, AVException e) {
                                       if(e == null)
                                       {
                                           L.d("123");
                                           for(OcrList a :list)
                                           {
                                                mOcrLists.add(a);
                                               spinner_list.add(a.getList_name());
                                               ocrRecycViewAdapter.notifyDataSetChanged();

                                           }
                                       }else {
                                           L.d("失败");
                                       }
                                   }
                               });



    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getActivity().getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void initAccessToken() {

        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getActivity().getApplicationContext());
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getActivity().getApplicationContext(), AK, SK);
    }

    private void alertText(final String title, final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    private void infoPopText(final String result) {
      /*  alertText("", result);
        Log.d("TAG", "infoPopText: "+result);*/
        StringToJSON(result);
        SaveTheContent();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "需要android.permission.READ_PHONE_STATE", Toast.LENGTH_LONG).show();
        }
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GENERAL && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneral(FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            infoPopText(result);
                        }
                    });
        }
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralBasic(FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            infoPopText(result);
                        }
                    });
        }
        if (requestCode == REQUEST_CODE_GENERAL_ENHANCED && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralEnhanced(FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            infoPopText(result);
                        }
                    });
        }
        if (requestCode == REQUEST_CODE_GENERAL_WEBIMAGE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recWebimage(FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            infoPopText(result);
                        }
                    });
        }
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            RecognizeService.recBankCard(FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            infoPopText(result);
                        }
                    });
        }
    }

    public void StringToJSON( String result) {
        Gson gson = new Gson();
        Test test = gson.fromJson(result,Test.class);
        for(int i= 0;i<test.getWords_result().size();i++)
        {
            content.append(test.getWords_result().get(i).getWords());
        }

    /*    Test.WordsResultBean test2 = gson.fromJson(result,Test.WordsResultBean.class);
        orc_content.setText("22"+test2.getWords()+"88");*/


    }
    public void  initDlalogView()
    {
/*        dialogview = mInflater.inflate(R.layout.dialog_ocr_layout,null);
        spinner_ocr_list = (Spinner) dialogview.findViewById(R.id.ocr_list_spinner);
        spinner_add_button = (Button) dialogview.findViewById(R.id.add_spinner_button);
        spinner_add_item = (EditText) dialogview.findViewById(R.id.spinner_add_listitem);
        finish_button = (Button) dialogview.findViewById(R.id.finish_spinner_button);
        initSpinner();*/
    }

    public  void  SaveTheContent()
    {
        dialogview = mInflater.inflate(R.layout.dialog_ocr_layout,null);
        materialEditText = (MaterialEditText)dialogview.findViewById(R.id.ocr_contents);
        spinner_ocr_list = (Spinner) dialogview.findViewById(R.id.ocr_list_spinner);
        spinner_add_button = (Button) dialogview.findViewById(R.id.add_spinner_button);
        spinner_add_item = (EditText) dialogview.findViewById(R.id.spinner_add_listitem);
        finish_button = (Button) dialogview.findViewById(R.id.finish_spinner_button);
        initSpinner();

        spinner_ocr_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnertext = spinner_ocr_list.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.setMessage("                              保存")
                .setView(dialogview);
               dialog .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String context = spinner_add_item.getText().toString();
                        //判断添加想是否为空
                        String text = materialEditText.getText().toString();

                        if(!context.equals(""))
                        {
                            spinner_adapter.add(context);
                            final OcrList ocrlist = new OcrList();
                            ocrlist.setList_name(context);
                            ocrlist.setUserId(AVUser.getCurrentUser().getObjectId());
                            ocrlist.AddArraylist(text);
                            ocrlist.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null){
                                        L.d("SuccessSS" +  ocrlist.getObjectId());
                                        String id;
                                        id =ocrlist.getObjectId() ;
                                        SetId(ocrlist,id);

                                    }else {
                                        L.d("failed"+e.toString());
                                    }
                                }
                            });
                            mOcrLists.add(ocrlist);
                            content= new StringBuilder();
                            int position = spinner_adapter.getPosition(context);
                            spinner_ocr_list.setSelection(position);
                            //清空用于输入的EditText
                            context = "";
                            spinner_add_item.setText("");
                            spinnertext = "";
                        }

                        for(int i = 0;i < spinner_adapter.getCount();i++)
                        {
                            if(context.equals(spinner_adapter.getItem(i)))
                            {
                              return;
                            }

                        }
                        for (int i = 0;i < mOcrLists.size();i++)
                        {
                            if(spinnertext.equals(mOcrLists.get(i).getList_name()))
                            {
                                 OcrList ocrList = new OcrList();
                                mOcrLists.get(i).AddArraylist(text);
                                ocrList.setObjectId(mOcrLists.get(i).getId());
                                L.d("m33"+mOcrLists.get(i).getId());
                                L.d("m33"+mOcrLists.get(i).getList_name());
                                L.d("m33"+mOcrLists.get(i).getArrayList());
                                ocrList.setArrayList((ArrayList<String>) mOcrLists.get(i).getArrayList());
                                ocrList.saveInBackground();
                            /*    AVQuery<OcrList> query = AVQuery.getQuery(OcrList.class);
                               query.findInBackground(new FindCallback<OcrList>() {
                                   @Override
                                   public void done(List<OcrList> list, AVException e) {
                                       if(e == null)
                                       {
                                           for(OcrList a :list)
                                           {
                                               L.d(a.getArrayList().toString());
                                           }
                                       }
                                   }
                               });*/
                                content= new StringBuilder();
                            }
                        }


                        spinner_ocr_list.setVisibility(View.VISIBLE);
                        spinner_add_item.setVisibility(View.GONE);
                        spinner_add_button.setVisibility(View.VISIBLE);
                        finish_button.setVisibility(View.GONE);
                        ocrRecycViewAdapter.notifyDataSetChanged();
                        content= new StringBuilder();

                    }
                });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        dialog.show();
        materialEditText.setText(content);
        spinner_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerAdd();
            }
        });


    }

    private void SetId(OcrList ocrList, String id) {

        ocrList.setId(id);

        ocrList.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {

            }
        });
    }


    private void initSpinner() {


        spinner_adapter = new ArrayAdapter<String>(
                getActivity().getApplication(),R.layout.support_simple_spinner_dropdown_item
                ,spinner_list);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_ocr_list.setAdapter(spinner_adapter);
    }

    public void SpinnerAdd()
    {
        spinner_ocr_list.setVisibility(View.GONE);
        spinner_add_item.setVisibility(View.VISIBLE);
        spinner_add_button.setVisibility(View.GONE);
        finish_button.setVisibility(View.VISIBLE);
    }
    private void Spinnerfinish() {


    }

    private void AddOcrList(String context)
    {

    }

}
