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
        robot.fourBar.setPosition(0.9 );
        waitForStart();
        timeLeft.reset();
        robot.MoveInchEncoder(.4,2150);
        robot.moveLift(1,"high");
        sleep(2000);
        robot.fourBar.setPosition(.6);
        sleep(2000);
        robot.Strafe(-.4,600);
        robot.MoveInchEncoder(.4,350);
        sleep(1000);
        robot.claw(false);
        sleep(500);
        /*while(timeLeft.seconds() <= 23) {
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
        }*/
        sleep(10000);
    }
}
