package org.d.network.tlycs;

        import org.d.model.lycs.Charities;

        import retrofit2.http.GET;
        import rx.Observable;

public interface TheLifeYouCanSaveService {
    @GET("DesktopModules/DnnSharp/DnnApiEndpoint/Api.ashx?method=CharitiesJSON")
    Observable<Charities> getCharities();
}
