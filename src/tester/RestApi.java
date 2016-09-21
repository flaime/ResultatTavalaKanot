package tester;

//public class RestApi {
//
//}



import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
//import retrofit2.GsonConverterFactory; //denna komenterade jag bort då den klagade
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RestApi {
	
	
	//Komenterade bort allt så den ska sluta klaga hela tiden ;) 

	private static final String GITHUB_ENDPOINT = "https://api.github.com";
	/*private final GitHubService mService;

	public interface GitHubService {
		@GET("/users/{user}/repos") Call<List<Repo>> listRepos(@Path("user") String user);
	}

	public RestApi() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(GITHUB_ENDPOINT)
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		mService = retrofit.create(GitHubService.class);
	}

	public void listRepositories(String saulmm) {
		mService.listRepos(saulmm).enqueue(new Callback<List<Repo>>() {
			@Override public void onResponse(Response<List<Repo>> response, Retrofit retrofit) {
				System.out.println("[DEBUG]" + " RestApi onResponse Number of repositories- " +response.body().size());
			}

			@Override public void onFailure(Throwable t) {
				System.out.println("[DEBUG]" + " RestApi onFailure - " +
					"");
			}
		});
	}*/
}
