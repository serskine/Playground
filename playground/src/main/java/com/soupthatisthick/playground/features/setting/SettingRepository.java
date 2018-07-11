package com.soupthatisthick.playground.features.setting;

import com.soupthatisthick.playground.features.setting.model.Setting;
import com.soupthatisthick.playground.features.setting.model.SettingEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface SettingRepository extends JpaRepository<SettingEntity, String> {

	@Query("select new com.soupthatisthick.playground.features.setting.model.Setting(e) from SettingEntity e where e.name = :name")
	Setting findByName(@NotNull @Param("name") final String name);

	@Query("select new com.soupthatisthick.playground.features.setting.model.Setting(e) from SettingEntity e where e.name like :searchTerm% order by e.name asc")
	List<Setting> listByNameStartingWith(
		@NotNull @Param("searchTerm") final String searchTerm,
		Pageable pageable
	);

	@Query("select new com.soupthatisthick.playground.features.setting.model.Setting(e) from SettingEntity e order by e.name asc")
	List<Setting> listAll(Pageable pageable);
}
