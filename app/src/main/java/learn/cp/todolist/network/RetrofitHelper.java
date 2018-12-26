package learn.cp.todolist.network;


import com.example.common.retrofit_network.common.Constants;
import com.example.common.retrofit_network.common.IdeaApi;

public class RetrofitHelper {
    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService() {
        if (mIdeaApiService == null) {
            mIdeaApiService = IdeaApi.getApiService(IdeaApiService.class, Constants.HOST);
        }
        return mIdeaApiService;
    }
}
