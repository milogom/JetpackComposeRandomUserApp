package com.example.composedemo.api
import com.example.composedemo.model.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

@RunWith(MockitoJUnitRunner::class)
class ApiServiceTest {

    @Mock
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        // Configuración inicial, si es necesaria.
    }

    @Test
    fun `test obtener usuario exitosamente`() = runBlocking {
        // Preparar datos de prueba
        val userMock = User().apply {
            picture = Picture().apply {
                large = "url_large"
                medium = "url_medium"
                thumbnail = "url_thumbnail"
            }
            name = Name().apply {
                first = "Test"
                last = "User"
            }
            email = "test@example.com"
            gender = "female"
            phone = "123-456-7890"
            location = Location().apply {
                street = Street().apply {
                    number = 123
                    name = "Main St"
                }
                city = "Springfield"
                state = "State"
                country = "Country"
                postcode = "12345"
                coordinates = Coordinates().apply {
                    latitude = "00.0000"
                    longitude = "-00.0000"
                }
            }
            registered = Registered().apply {
                date = "2023-01-01"
                age = 30
            }
        }

        // Configurar el comportamiento del mock
        `when`(apiService.getUser()).thenReturn(Response.success(userMock))

        // Ejecutar la operación de la API
        val response = apiService.getUser()

        // Verificar los resultados
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals("Test", response.body()?.name?.first)
        assertEquals("Springfield", response.body()?.location?.city)
        assertEquals("2023-01-01", response.body()?.registered?.date)
        assertEquals(30, response.body()?.registered?.age)
    }
}
