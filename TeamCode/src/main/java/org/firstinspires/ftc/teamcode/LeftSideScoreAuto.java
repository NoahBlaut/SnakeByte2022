package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.utils.AutoMethods;
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;


@Autonomous(name = "Left Side Score Auto", group = "Auto")
public class LeftSideScoreAuto extends LinearOpMode {

    int tagOfInterest = 0;
    ElapsedTime timeLeft = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Camera/Robot Setup
        AutoMethods robot = new AutoMethods();
        robot.ready(this);
        AprilTagDetectionPipeline aprilTagDetectionPipeline = robot.cameraSetup(this);

        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> currentDetections = robot.getTag(aprilTagDetectionPipeline);

            if (currentDetections.size() != 0) {
                boolean tagFound = false;
                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == 1 || tag.id == 2 || tag.id == 3) {
                        tagOfInterest = tag.id;
                        tagFound = true;
                        break;
                    }
                }
                if (tagFound) {
                    telemetry.addData("Tag Found:", tagOfInterest);
                }
            } else {
                telemetry.addLine("Tag not currently found: ");

                if (tagOfInterest == 0) {
                    telemetry.addLine("(no tags have been seen)");
                } else {
                    telemetry.addLine("\nBut the tag has been seen before, Tag ID: " + tagOfInterest);
                }
            }
            telemetry.update();
        }

        waitForStart();
        timeLeft.reset();
        robot.MoveInchEncoder(.6,1960);
        robot.moveLift(1,4000);
        robot.fourBar.setPower(.6);
        robot.Strafe(-.6,600);
        sleep(500);
        robot.fourBar.setPower(0);
        robot.MoveInchEncoder(.4,350);
        robot.fourBar.setPower(-1);
        robot.claw(false);
        sleep(500);
        double ticksToCones = 1540;
        int liftPos = 240;;
        while(timeLeft.seconds() <= 23) {

            robot.MoveInchEncoder(-.6, 200);
            robot.moveLift(1,liftPos);
            robot.rotation(-.6, 90);
            robot.fourBar.setPower(-.6);
            robot.MoveInchEncoder(1, ticksToCones);
            robot.claw(true);
            sleep(250);
            robot.fourBar.setPower(.6);
            robot.MoveInchEncoder(-.6, ticksToCones);
            robot.moveLift(1, 4000);
            robot.rotation(.6, 90);
            robot.MoveInchEncoder(.4, 200);
            robot.fourBar.setPower(-1);
            robot.claw(false);
            ticksToCones-=20;
            liftPos -=40;
            sleep(250);
        }
        if(tagOfInterest == 1) {
            robot.Strafe(.6,2500);
        } else if(tagOfInterest == 2) {
            robot.MoveInchEncoder(.6,600);
        } else if (tagOfInterest == 3) {
            robot.Strafe(-.6,660);
        } else {
            telemetry.clearAll();
            telemetry.addLine("FATAL ERROR: NO TAGS FOUND");
            telemetry.update();
        }
    }
}

