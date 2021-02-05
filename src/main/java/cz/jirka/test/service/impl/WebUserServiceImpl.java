package cz.jirka.test.service.impl;

import cz.jirka.test.service.WebUserService;
import cz.jirka.test.domain.WebUser;
import cz.jirka.test.repository.WebUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WebUser}.
 */
@Service
@Transactional
public class WebUserServiceImpl implements WebUserService {

    private final Logger log = LoggerFactory.getLogger(WebUserServiceImpl.class);

    private final WebUserRepository webUserRepository;

    public WebUserServiceImpl(WebUserRepository webUserRepository) {
        this.webUserRepository = webUserRepository;
    }

    @Override
    public WebUser save(WebUser webUser) {
        log.debug("Request to save WebUser : {}", webUser);
        return webUserRepository.save(webUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebUser> findAll() {
        log.debug("Request to get all WebUsers");
        return webUserRepository.findAllWithEagerRelationships();
    }


    public Page<WebUser> findAllWithEagerRelationships(Pageable pageable) {
        return webUserRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WebUser> findOne(Long id) {
        log.debug("Request to get WebUser : {}", id);
        return webUserRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebUser : {}", id);
        webUserRepository.deleteById(id);
    }
}
