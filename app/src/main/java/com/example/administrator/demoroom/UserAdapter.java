package com.example.administrator.demoroom;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.demoroom.db.UserEntity;
import com.example.administrator.demoroom.utils.onMoveAndSwipedListener;

/**
 * @author MaoYiHan
 * @date 2018/8/1
 */
public class UserAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> implements onMoveAndSwipedListener {
    private OnDeleteListener mOnDeleteListener;

    public UserAdapter() {
        super(R.layout.adapter_users);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserEntity item) {
        helper.setText(R.id.tvNum, item.getUid() + "");
        helper.setText(R.id.tvName, item.getName());
        helper.setText(R.id.tvAddress, item.getAddress());
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        if (mOnDeleteListener != null) {
            mOnDeleteListener.onDelete(this.getData().get(position));
        }
        UserAdapter.this.getData().remove(position);
        notifyItemRemoved(position);
    }

    public interface OnDeleteListener {
        void onDelete(UserEntity userEntity);
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }
}
