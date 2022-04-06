package com.chen.mygooglemap.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chen.mygooglemap.Application
import com.chen.mygooglemap.domain.models.Geometry
import com.chen.mygooglemap.domain.models.GeometryLocation
import com.chen.mygooglemap.domain.models.PlaceDomainModel
import com.chen.mygooglemap.domain.usecaes.GetLocationUseCase
import com.chen.mygooglemap.domain.usecaes.GetNearPlacesUsecase
import com.chen.mygooglemap.models.LocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

/**
 * MapViewModel unit test
 */
class MapViewmodelTest {
    private lateinit var viewmodel: MapViewModel
    private lateinit var getLocationUseCase: GetLocationUseCase
    private lateinit var getNearPlacesUsecase: GetNearPlacesUsecase
    private lateinit var applicationContext: Application

    private val location: LocationModel = LocationModel(latitude = 24.670808, longitude = 60.1960315)
    private val place1: PlaceDomainModel = PlaceDomainModel(id = "1001", name = "Place 1", icon = "",
                                                geometry = Geometry(location = GeometryLocation(lat = 24.736785, lng = 60.346874)))
    private val place2: PlaceDomainModel = PlaceDomainModel(id = "1002", name = "Place 2", icon = "",
                                                geometry = Geometry(location = GeometryLocation(lat = 24.347787, lng = 60.749549)))
    private val placeList: List<PlaceDomainModel> = listOf(place1, place2)
    private val map_key: String = "Azewuiietotukdfgjflkgcvnbbvmcm598695"

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val dispatcher = TestCoroutineDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        applicationContext = mock(Application::class.java)
        getLocationUseCase = mock(GetLocationUseCase::class.java)
        getNearPlacesUsecase = mock(GetNearPlacesUsecase::class.java)

        viewmodel = MapViewModel(getLocationUseCase, getNearPlacesUsecase)
    }

    @Test
    fun getLocation() = runBlocking {
        runBlocking {
            given(getLocationUseCase.build().blockingFirst().mapToPresentation() ).willReturn(location)
            viewmodel.getCurrentLocation()

            val currentLoc = viewmodel.currentLocation.value
            Assert.assertTrue(currentLoc == location)
        }
    }

    @Test
    fun getNearByPlaces() = runBlocking {
        runBlocking {
            given(getNearPlacesUsecase.build(map_key, "${location.latitude},${location.longitude}").blockingFirst()).willReturn(placeList)
            viewmodel.getNearByPlaces(applicationContext, location)

            val nearPlaces = viewmodel.nearByPlaces.value
            Assert.assertTrue(nearPlaces?.get(0) == place1)
            Assert.assertTrue(nearPlaces?.get(1)  == place2)
        }
    }
}
