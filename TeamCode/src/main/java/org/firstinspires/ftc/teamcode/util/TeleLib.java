package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class TeleLib {
    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;

    public DcMotor lift;
    public DcMotor fB;

    public Servo clamp;

    OpMode opMode;
    public boolean isOpen = true;
    // front or back = true means front, false means back
    public boolean frontOrBack = true;

    public TeleLib(OpMode opMode) {
        fl = opMode.hardwareMap.dcMotor.get("fl");
        fr = opMode.hardwareMap.dcMotor.get("fr");
        bl = opMode.hardwareMap.dcMotor.get("bl");
        br = opMode.hardwareMap.dcMotor.get("br");
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setDirection(DcMotor.Direction.REVERSE);
        fl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);

        lift = opMode.hardwareMap.dcMotor.get("lift1");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fB = opMode.hardwareMap.dcMotor.get("4Bar");
        fB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        clamp = opMode.hardwareMap.servo.get("clamp");


        resetEncoders();
        resetLiftEncoder();

    }

    public void resetEncoders() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void resetLiftEncoder() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drivetrain(OpMode opMode) {
        if(Math.abs(opMode.gamepad1.left_stick_x) > 0.1 || Math.abs(opMode.gamepad1.left_stick_y) > 0.1 || Math.abs(opMode.gamepad1.right_stick_x) > 0.1) {
            double x = -opMode.gamepad1.left_stick_x;
            double y = opMode.gamepad1.left_stick_y;
            double rx = -opMode.gamepad1.right_stick_x;
            double denom = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double FLP = (y - x + rx) / denom;
            double BLP = (y + x + rx) / denom;
            double FRP = (y - x - rx) / denom;
            double BRP = (y + x - rx) / denom;

            if (opMode.gamepad1.right_trigger > 0.1) {
                fl.setPower(FLP * .35);
                fr.setPower(FRP * .35);
                bl.setPower(BLP * .35);
                br.setPower(BRP * .35);
            } else {
                fl.setPower(FLP);
                fr.setPower(FRP);
                bl.setPower(BLP);
                br.setPower(BRP);
            }
        }
        else{
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        }
    }
    
    public void lift(OpMode opMode) throws InterruptedException {
        if(Math.abs(opMode.gamepad2.left_stick_y) > 0.1) {
            if (opMode.gamepad2.left_trigger > .2) {
                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setPower(opMode.gamepad2.left_stick_y * .5);
            } else {
                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setPower(opMode.gamepad2.left_stick_y);
            }
        } else {
            lift.setTargetPosition(lift.getCurrentPosition());
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(.5);
        }

        if (opMode.gamepad2.right_bumper) {
            if (isOpen) {
                clamp.setPosition(0.5);
            } else {
                clamp.setPosition(0);
            }
            isOpen = !isOpen;
            opMode.telemetry.addData("isOpen", isOpen);
            opMode.telemetry.update();
            while (opMode.gamepad2.right_bumper) {
                Thread.sleep(100);
            }
        }
        if(opMode.gamepad2.left_bumper){
            frontOrBack = !frontOrBack;
            if(frontOrBack){
                opMode.telemetry.addLine("front");
            } else{
                opMode.telemetry.addLine("back");
            }
            opMode.telemetry.update();
            while (opMode.gamepad2.right_bumper) {
                Thread.sleep(100);
            }
        }
        if(Math.abs(opMode.gamepad2.right_stick_y) > 0.1){
            if(opMode.gamepad2.right_trigger > 0.1){
                fB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                fB.setPower(opMode.gamepad2.left_stick_y * .375);
            }else{
                fB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                fB.setPower(opMode.gamepad2.left_stick_y*.75);
            }
        } else{
            fB.setTargetPosition(fB.getCurrentPosition());
            fB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fB.setPower(.1);
        }
        if(opMode.gamepad2.x){
            liftToPos(0,.75);
            fbToPos(0,.5);
            clamp.setPosition(.5);
        } else{
            fbToPos(fB.getCurrentPosition(),.1);
            liftToPos(lift.getCurrentPosition(), .5);
        }
        if(opMode.gamepad2.a){
            clamp.setPosition(0.5);
            liftToPos(0,.75);
            if(frontOrBack){
                fbToPos(90,.5);
            } else{
                fbToPos(245,.5);
            }
        } else{
            fbToPos(fB.getCurrentPosition(),.1);
            liftToPos(lift.getCurrentPosition(), .5);
        }
        if(opMode.gamepad2.b){
            clamp.setPosition(0.5);
            if(frontOrBack){
                fbToPos(90,.5);
            } else{
                fbToPos(245,.5);
            }
            liftToPos(540,.75);
        } else{
            fbToPos(fB.getCurrentPosition(),.1);
            liftToPos(lift.getCurrentPosition(), .5);
        }
        if(opMode.gamepad2.y){
            clamp.setPosition(0);
            if(frontOrBack){
                fbToPos(90,.5);
            } else{
                fbToPos(245,.5);
            }
            liftToPos(1080,.75);
        } else{
            fbToPos(fB.getCurrentPosition(),.1);
            liftToPos(lift.getCurrentPosition(), .5);
        }
    }

    public void liftToPos(int targetPos, double power){
        lift.setTargetPosition(targetPos);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(.85);
    }

    public void fbToPos(int targetPos, double power){
        fB.setTargetPosition(targetPos);
        fB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fB.setPower(.5);
    }
    public void kill() {
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        lift.setPower(0);
    }
}
