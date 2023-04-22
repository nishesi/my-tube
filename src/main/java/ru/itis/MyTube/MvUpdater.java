package ru.itis.MyTube;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MvUpdater extends Thread {
    private static final String SQL_UPDATE_MV =
            "refresh materialized view channels_inf;" +
                    "refresh materialized view videos_inf;";
    private final DataSource dataSource;
    private final long period;
    private boolean isRun = false;

    @Override
    public void run() {
        isRun = true;
        while (isRun) {
            update();
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void update() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_MV)) {

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void finish() {
        isRun = false;
        this.notify();
    }
}
