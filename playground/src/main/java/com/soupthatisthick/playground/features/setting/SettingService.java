package com.soupthatisthick.playground.features.setting;

import com.soupthatisthick.playground.features.setting.model.ListSettingsRequest;
import com.soupthatisthick.playground.features.setting.model.SaveSettingRequest;
import com.soupthatisthick.playground.features.setting.model.Setting;
import com.soupthatisthick.playground.features.setting.model.SettingEntity;
import com.soupthatisthick.playground.util.exception.AppException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.soupthatisthick.playground.util.logger.Logger.LOG;

@Service
public class SettingService {

	@Autowired
	private SettingRepository settingRepository;

	@Transactional
	public void save(@NotNull SaveSettingRequest request) {
		SettingEntity settingEntity = settingRepository.findOne(request.getName());
		if (settingEntity==null) {
			settingEntity = new SettingEntity();
		}
		settingEntity.setName(request.getName());
		settingEntity.setValue(request.getValue());
		settingRepository.save(settingEntity);
	}

	/**
	 * This will look for the specific setting by name
	 * @param name identifies the setting
	 * @return a {@link Setting} containing it's value as a string
	 */
	public Setting read(@NotNull String name) {
		Setting setting = settingRepository.findByName(name);
		if (setting==null) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Setting does not exist", null);
		}
		return setting;
	}

	/**
	 * Finds all the settings that match the specified search term. If the search term
	 * is null then it matches all settings. The search is case sensitive.
	 * @param request a {@link ListSettingsRequest} that determines what is returned
	 * @return a {@link List<Setting>} for all matching settings
	 */
	public List<Setting> list(@NotNull ListSettingsRequest request) {
		if (request.getSearchTerm()==null) {
			return settingRepository.listAll(request.asPageable());
		} else {
			return settingRepository.listByNameStartingWith(
				request.getSearchTerm().toLowerCase(),
				request.asPageable()
			);
		}
	}

	/**
	 * This will delete a setting
	 * @param name identifies the desired setting
	 */
	public void delete(@NotNull String name) {
		read(name);
		try {
			settingRepository.delete(name);
		} catch (Exception e) {
			throw new AppException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	// ---------------------------------------------------- //
}
