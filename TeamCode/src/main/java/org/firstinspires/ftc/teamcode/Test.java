package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.AutoMethods;

@Autonomous(name = "test")

public class Test extends LinearOpMode {
    AutoMethods robot = new AutoMethods();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.ready(this);
        robot.claw(true);
        waitForStart();
        sleep(100);
        robot.MoveInchEncoder(.4,1150);
        robot.fourBar.setPower(-.7);
        sleep(900);
        robot.fourBar.setPower(0);
        //sleep(1000);
        robot.MoveInchEncoder(.4,1100);
        robot.moveLift(1,4000);
        robot.Strafe(-.7, 500);
        sleep(250);
        robot.fourBar.setPower(1);
        robot.claw(false);
        sleep(1500);
        /*
        *   robot.rotation(-.5,90);
        *   robot.moveLift(1,240);
        *   robot.MoveInchEncoder(1,900);
        *   robot.MoveInchEncoder(.4, 360);
        *   robot.claw(true);
        *   robot.moveLift(1,350);
        *   robot.MoveInchEncoder(-.6, 900);
        *   robot.rotation(-.5,90);
        *   robot.moveLift(1,4000);
        *   robot.MoveInchEncoder(-.6, 250);
        *   robot.fourBar.setPower(-1);
        *   sleep(2000);
        *   robot.fourBar.setPower(0);
        *   robot.claw(false);
        *   sleep(1500);
        *   robot.MoveInchEncoder(.6,250);
        *   robot.rotation(.5, 45);
        *   repeat this
        */
    }
}
