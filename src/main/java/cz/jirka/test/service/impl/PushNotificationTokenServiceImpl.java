package cz.jirka.test.service.impl;

import cz.jirka.test.service.PushNotificationTokenService;
import cz.jirka.test.domain.PushNotificationToken;
import cz.jirka.test.repository.PushNotificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PushNotificationToken}.
 */
@Service
@Transactional
public class PushNotificationTokenServiceImpl implements PushNotificationTokenService {

    private final Logger log = LoggerFactory.getLogger(PushNotificationTokenServiceImpl.class);

    private final PushNotificationTokenRepository pushNotificationTokenRepository;

    public PushNotificationTokenServiceImpl(PushNotificationTokenRepository pushNotificationTokenRepository) {
        this.pushNotificationTokenRepository = pushNotificationTokenRepository;
    }

    @Override
    public PushNotificationToken save(PushNotificationToken pushNotificationToken) {
        log.debug("Request to save PushNotificationToken : {}", pushNotificationToken);
        return pushNotificationTokenRepository.save(pushNotificationToken);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PushNotificationToken> findAll() {
        log.debug("Request to get all PushNotificationTokens");
        return pushNotificationTokenRepository.findAllWithEagerRelationships();
    }


    public Page<PushNotificationToken> findAllWithEagerRelationships(Pageable pageable) {
        return pushNotificationTokenRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PushNotificationToken> findOne(Long id) {
        log.debug("Request to get PushNotificationToken : {}", id);
        return pushNotificationTokenRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PushNotificationToken : {}", id);
        pushNotificationTokenRepository.deleteById(id);
    }
}
