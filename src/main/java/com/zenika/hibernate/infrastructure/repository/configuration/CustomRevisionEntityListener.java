package com.zenika.hibernate.infrastructure.repository.configuration;

import com.zenika.hibernate.infrastructure.repository.model.CustomRevisionEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomRevisionEntityListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        log.info("New revision: {}", revisionEntity);
        if (revisionEntity instanceof CustomRevisionEntity customRevisionEntity) {
            customRevisionEntity.setUsername("TODO: Current username");
        }
    }
}
