package com.layidao.httpdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.layidao.httpdemo.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 */
public class MyQiushiAdapter extends BaseAdapterHelper<Map<String, String>> {
    private final static int TYPE1 = 0, TYPE2 = 1;
    private Context context = null;
    private ViewHolder1 mHolder1;
    private ViewHolder2 mHolder2;
    private List<Map<String, String>> list = null;

    public MyQiushiAdapter(List<Map<String, String>> list, Context context) {
        super(list, context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        String imageUrl = getImageUrl(list.get(position).get("image"));
        return imageUrl.equals("") ? TYPE1 : TYPE2;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<Map<String, String>> list, LayoutInflater inflater) {
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE1:
                    convertView = inflater.inflate(R.layout.item_listview_main1, parent, false);
                    mHolder1 = new ViewHolder1(convertView);
                    convertView.setTag(mHolder1);
                    break;
                case TYPE2:
                    convertView = inflater.inflate(R.layout.item_listview_main2, parent, false);
                    mHolder2 = new ViewHolder2(convertView);
                    convertView.setTag(mHolder2);
                    break;
            }
        } else {
            switch (type) {
                case TYPE1:
                    mHolder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TYPE2:
                    mHolder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case TYPE1:
                mHolder1.textView_item_content.setText(list.get(position).get("content"));
                mHolder1.textView_item_login.setText(list.get(position).get("login"));
                mHolder1.textView_item_commentscount.setText(list.get(position).get("comments_count"));

                final String imageUrl = getImageUrl(list.get(position).get("image"));
                if (!imageUrl.equals("")) {
                    mHolder1.imageView_item_show.setImageResource(R.mipmap.ic_launcher);
                }

            case TYPE2:
                mHolder2.textView_item_content.setText(list.get(position).get("content"));
                mHolder2.textView_item_login.setText(list.get(position).get("login"));
                mHolder2.textView_item_commentscount.setText(list.get(position).get("comments_count"));
                break;
        }
        return convertView;
    }

    // 根据图片的名称拼凑图片的网络访问地址
    private String getImageUrl(String imageName){
        String urlFirst = "", urlSecond = "";
        if (imageName.indexOf('.') > 0 ){
            StringBuilder sb= new StringBuilder();
            if (imageName.indexOf("app") == 0){
                urlSecond = imageName.substring(3,imageName.indexOf('.'));
                switch (urlSecond.length()) {
                    case 8:
                        urlFirst = imageName.substring(3, 7);
                        break;
                    case 9:
                        urlFirst = imageName.substring(3, 8);
                        break;
                    case 10:
                        urlFirst = imageName.substring(3, 9);
                        break;
                }
            }else{
                urlSecond = imageName.substring(0,imageName.indexOf('.'));
                urlFirst = imageName.substring(0,6);
            }

            sb.append("http://pic.qiushibaike.com/system/pictures/");
            sb.append(urlFirst);
            sb.append("/");
            sb.append(urlSecond);
            sb.append("/");
            sb.append("small/");
            sb.append(imageName);
            return sb.toString();
        }else{
            return "";
        }
    }

    // 重新加载所有数据
    public void reloadData(List<Map<String,String>> data, boolean isClear){
        if (isClear){
            list.clear();
        }
        list.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder2{
        private TextView textView_item_content;
        private TextView textView_item_login;
        private TextView textView_item_commentscount;

        public ViewHolder2(View convertView){
            textView_item_content = ((TextView) convertView.findViewById(R.id.textView_item_content));
            textView_item_login = ((TextView) convertView.findViewById(R.id.textView_item_login));
            textView_item_commentscount = ((TextView) convertView.findViewById(R.id.textView_item_commentscount));
        }
    }

    class ViewHolder1{
        private ImageView imageView_item_show;
        private TextView textView_item_content;
        private TextView textView_item_login;
        private TextView textView_item_commentscount;

        public ViewHolder1(View convertView){
            imageView_item_show = ((ImageView) convertView.findViewById(R.id.imageView_item_show));
            textView_item_content = ((TextView) convertView.findViewById(R.id.textView_item_content));
            textView_item_login = ((TextView) convertView.findViewById(R.id.textView_item_login));
            textView_item_commentscount = ((TextView) convertView.findViewById(R.id.textView_item_commentscount));
        }
    }

}
