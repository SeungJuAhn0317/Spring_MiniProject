package com.example.mentor.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mentor.model.Point;

public interface PointRepository extends JpaRepository<Point, Long>{
	@Query(nativeQuery = true, value = "select p.*, (6371 * acos(cos(radians(?1)) * cos(radians(p.latitue)) * cos(radians(p.longitude) - radians(?2)) + sin(radians(?3)) * sin(radians(p.latitude)))) as distance from Point p having distance <= 3 order by distance")
	public List<Point> findByLatLng(double lat1, double lng, double lat2);
}