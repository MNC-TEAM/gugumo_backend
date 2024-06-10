package sideproject.gugumo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.gugumo.domain.entity.notification.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
