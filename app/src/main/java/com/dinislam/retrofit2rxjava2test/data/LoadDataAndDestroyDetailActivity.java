package com.dinislam.retrofit2rxjava2test.data;

import com.dinislam.retrofit2rxjava2test.pojo.Comment;

import java.util.List;

public interface LoadDataAndDestroyDetailActivity {
    void destroyActivity();
    void setCommentsInAdapter(List<Comment> list);
//    void setCommentsInAdapterWithReplace(List<Comment> list);
}
