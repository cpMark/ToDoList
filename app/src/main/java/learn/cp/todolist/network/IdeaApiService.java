package learn.cp.todolist.network;


import com.example.common.okhttp_network.BaseBean;
import com.example.common.retrofit_network.common.BasicResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 */

public interface IdeaApiService {

    ///////////////////////////////////////////////////////////////////////////
    // 用户模块
    ///////////////////////////////////////////////////////////////////////////

    /**
     *  注册
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<BasicResponse<UserBean>> registerAccount(@Field("account") String account, @Field("pwd")String password);

    /**
     *  登陆
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BasicResponse<UserBean>> login(@Field("account") String account, @Field("pwd")String password);

    ///////////////////////////////////////////////////////////////////////////
    // 待办任务模块
    ///////////////////////////////////////////////////////////////////////////

    /**
     *  查询未完成的待办任务
     */
    @GET("todo/query/unfinishedTask")
    Observable<BasicResponse<List<ToDoItemBean>>> queryUnfinishedTask(@Query("userId")String userId);

    /**
     *  新增待办任务
     */
    @POST("todo/insert/todoItem")
    Observable<BasicResponse<String>> insertToDoItem(@Field("userId")String userId, @Field("title")String title, @Field("content")String content);

    /**
     *  删除待办任务
     */
    @POST("todo/delete/todoItem")
    Observable<BasicResponse<String>> deleteToDoItem(@Field("userId")String userId,@Field("toDoId")String toDoId);

    /**
     *  更新待办任务
     */
    @POST("todo/update/todoItem")
    Observable<BasicResponse<String>> updateToDoItem(@Field("userId")String userId,@Field("toDoId")String toDoId,
                                                     @Field("title")String title, @Field("content")String content,
                                                     @Field("newStatus")Integer newStatus);
}
