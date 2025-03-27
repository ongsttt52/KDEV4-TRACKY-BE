package kernel360.trackyweb.biz.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackyweb.biz.infrastructure.repository.BizRepository;
import kernel360.trackyweb.biz.presentation.entity.BizEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BizService {

	private final BizRepository bizRepository;

	public List<BizEntity> getAll() {

		return bizRepository.findAll();
	}
}
