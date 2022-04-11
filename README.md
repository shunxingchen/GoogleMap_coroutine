# A demo project using Android Clean architecture

- Clean architecture (app, domain, data) three modules
- Kotlin
- Dagger 2
- Coroutine
- Android API support 21 - 31

# Show current location and nearby POIs

Currently just two usecases: 'GetLocationUsecase' and 'GetNearPlacesUsecase', can add more then like nearby events ...

- Current location regular update every 10s, and fast update is 6s
- Press 'Near places' to fetch and show nearby POIs, range:5km, limit:13. Moment just some POIs(Bank, Shopping, Restaurants, Bar, museum ...) using specific POI icons, all else still use default blue marker bloon, will add more POI icons ... 
