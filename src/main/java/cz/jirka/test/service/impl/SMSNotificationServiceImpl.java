package cz.jirka.test.service.impl;

import cz.jirka.test.service.SMSNotificationService;
import cz.jirka.test.domain.SMSNotification;
import cz.jirka.test.repository.SMSNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SMSNotification}.
 */
@Service
@Transactional
public class SMSNotificationServiceImpl implements SMSNotificationService {

    private final Logger log = LoggerFactory.getLogger(SMSNotificationServiceImpl.class);

    private final SMSNotificationRepository sMSNotificationRepository;

    public SMSNotificationServiceImpl(SMSNotificationRepository sMSNotificationRepository) {
        this.sMSNotificationRepository = sMSNotificationRepository;
    }

    @Override
    public SMSNotification save(SMSNotification sMSNotification) {
        log.debug("Request to save SMSNotification : {}", sMSNotification);
        return sMSNotificationRepository.save(sMSNotification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SMSNotification> findAll() {
        log.debug("Request to get all SMSNotifications");
        return sMSNotificationRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SMSNotification> findOne(Long id) {
        log.debug("Request to get SMSNotification : {}", id);
        return sMSNotificationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SMSNotification : {}", id);
        sMSNotificationRepository.deleteById(id);
    }
}
