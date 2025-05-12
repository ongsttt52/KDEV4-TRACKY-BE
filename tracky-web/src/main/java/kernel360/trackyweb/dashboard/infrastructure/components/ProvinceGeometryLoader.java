package kernel360.trackyweb.dashboard.infrastructure.components;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import kernel360.trackyweb.dashboard.domain.vo.ProvincePolygon;

@Component
public class ProvinceGeometryLoader {

	private final List<ProvincePolygon> provincePolygons = new ArrayList<>();

	public List<ProvincePolygon> getProvincePolygons() {
		return provincePolygons;
	}

	@PostConstruct
	public void loadPolygons() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		InputStream is = getClass().getClassLoader().getResourceAsStream("geojson/korea-provinces.geojson");
		if (is == null)
			throw new IllegalStateException("GeoJSON 파일을 찾을 수 없습니다.");

		JsonNode root = mapper.readTree(is);
		GeoJsonReader reader = new GeoJsonReader();

		for (JsonNode feature : root.get("features")) {
			String provinceName = feature.get("properties").get("CTP_KOR_NM").asText();
			JsonNode geometryNode = feature.get("geometry");
			Geometry geometry = reader.read(mapper.writeValueAsString(geometryNode));
			provincePolygons.add(new ProvincePolygon(provinceName, geometry));
		}
	}
}
