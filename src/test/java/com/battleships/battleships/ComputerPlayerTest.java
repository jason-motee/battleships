package com.battleships.battleships;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BattleshipGridConfig.class)
public class ComputerPlayerTest {

    @Autowired
    private ComputerPlayer computerPlayer;

    @Test
    public void computerGeneratesCoordinate() {
        assertThat(computerPlayer.getCoordinate()).isNotNull();
        assertThat(computerPlayer.getCoordinate()).isNotEmpty();
    }

}