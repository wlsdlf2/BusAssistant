package _Team.bus_assistant_backend.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceTest {
    @Test
    public void testCallApi() {
        // Arrange
        ApiService apiService = new ApiService();
        String station_id = "200000177";
        String route_id = "200000037";

        // Act
        String result;
        try {
            result = apiService.call_api(station_id, route_id);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown during API call");
            return;
        }

        // Assert
        assertNotNull(result);
        System.out.println(result);
        // assertTrue(result.contains("<response>")); // 응답 결과에 특정한 XML 태그가 포함되어 있는지 확인할 수 있음
    }
}