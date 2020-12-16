package com.athome.mvp.fragment_comments_mvp;

import com.athome.models.CommentModel;
import com.athome.models.ProductModel;

import java.util.List;

public interface FragmentCommentView {
    void onSuccess(CommentModel commentModel);
    void onFailed(String msg);


}
