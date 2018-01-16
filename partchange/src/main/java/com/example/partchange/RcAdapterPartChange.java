package com.example.partchange;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txs
 * @date 2018/01/16
 */

public class RcAdapterPartChange extends RecyclerView.Adapter<RcAdapterPartChange.MyViewHolder> {
    private Context context;
    /**
     * adapter传递过来的数据集合
     */
    private List<String> list = new ArrayList<>();
    /**
     * 变色数据的其实位置 position
     */
    private int beginChangePos;
    /**
     * 需要改变颜色的text
     */
    private String text;
    /**
     * text改变的颜色
     */
    private ForegroundColorSpan span;

    /**
     * 在MainActivity中设置text和span
     */
    public void setText(String text, ForegroundColorSpan span) {
        this.text = text;
        this.span = span;
    }

    public RcAdapterPartChange(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        /**如果没有进行搜索操作或者搜索之后点击了删除按钮 我们会在MainActivity中把text置空并传递过来*/
        if (text != null) {
            //获取匹配文字的 position
            beginChangePos = list.get(position).indexOf(text);
            // 文字的builder 用来做变色操作
            SpannableStringBuilder builder = new SpannableStringBuilder(list.get(position));
            //如果没有匹配到关键字的话 list.get(position).indexOf(text)会返回-1
            if (beginChangePos != -1) {
                //设置呈现的文字
                builder.setSpan(span, beginChangePos, beginChangePos + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mTvText.setText(builder);
            }
        } else {
            holder.mTvText.setText(list.get(position));
        }
        //点击监听
        holder.mLlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onItemClickListener {
        void onClick(View view, int pos);
    }

    /**
     * Recyclerview的点击监听接口
     */
    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(RcAdapterPartChange.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLlItem;
        private SimpleDraweeView mImgvSimple;
        private TextView mTvText;

        public MyViewHolder(View itemView) {
            super(itemView);
            mLlItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mImgvSimple = (SimpleDraweeView) itemView.findViewById(R.id.imgv_simple);
            mTvText = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}
