package kernel360.trackyweb.dashboard.infrastructure.components;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import kernel360.trackyweb.dashboard.domain.vo.ProvincePolygon;

@Component
public class ProvinceMatcher {

	private final ProvinceGeometryLoader loader;

	public ProvinceMatcher(ProvinceGeometryLoader loader) {
		this.loader = loader;
	}

	public String findProvince(double lon, double lat) {
		Point point = new GeometryFactory().createPoint(new Coordinate(lon, lat));
		for (ProvincePolygon province : loader.getProvincePolygons()) {
			if (province.getGeometry().contains(point)) {
				return province.getName();
			}
		}
		return "Unknown";
	}
}
