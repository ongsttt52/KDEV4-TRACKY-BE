package kernel360.trackyweb.Biz.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kernel360.trackyweb.Biz.infrastructure.entity.BizEntity;
import kernel360.trackyweb.Biz.infrastructure.repository.BizRepository;
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
