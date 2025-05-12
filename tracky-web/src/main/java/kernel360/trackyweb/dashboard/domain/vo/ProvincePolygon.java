package kernel360.trackyweb.dashboard.domain.vo;

import org.locationtech.jts.geom.Geometry;

public class ProvincePolygon {
	private final String name;
	private final Geometry geometry;

	public ProvincePolygon(String name, Geometry geometry) {
		this.name = name;
		this.geometry = geometry;
	}

	public String getName() {
		return name;
	}

	public Geometry getGeometry() {
		return geometry;
	}
}

