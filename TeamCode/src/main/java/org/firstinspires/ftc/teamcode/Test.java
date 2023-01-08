package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.AutoMethods;

@Autonomous(name = "Test")
public class Test extends LinearOpMode {
    AutoMethods robot = new AutoMethods();
    ElapsedTime timeLeft = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.ready(this);
        waitForStart();
        timeLeft.reset();
        robot.MoveInchEncoder(.6,1960);
        robot.moveLift(1,"high");
        robot.fourBar.setPosition(.5);
        robot.Strafe(.6,280);
        robot.MoveInchEncoder(.4,350);
        robot.claw(false);
        sleep(250);
        while(timeLeft.seconds() <= 23) {
            robot.MoveInchEncoder(-.6, 200);
            robot.fourBar.setPosition(1);
            robot.rotation(.6, 90);
            robot.fourBar.setPosition(.85);
            robot.MoveInchEncoder(1, 1540);
            robot.claw(true);
            sleep(250);
            robot.MoveInchEncoder(-.6, 1540);
            robot.moveLift(1, "high");
            robot.fourBar.setPosition(.5);
            robot.rotation(-1, 90);
            robot.MoveInchEncoder(.4, 200);
            robot.claw(false);
            sleep(250);
        }

    }
}
