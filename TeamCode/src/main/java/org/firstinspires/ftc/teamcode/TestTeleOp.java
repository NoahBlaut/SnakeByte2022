package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name= "TestTele")
public class TestTeleOp extends OpMode {
    public DcMotor fl;
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public Servo clamp;
    public DcMotor fB;
    boolean isOpen;
    public DcMotor lift;
    int curPos = 0;
    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        fB = hardwareMap.dcMotor.get("4Bar");
        fB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        clamp = hardwareMap.servo.get("clamp");
        lift = hardwareMap.dcMotor.get("lift1");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        if(Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1) {
            double x = -gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            double denom = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double FLP = (y - x + rx) / denom;
            double BLP = (y + x + rx) / denom;
            double FRP = (y + x - rx) / denom;
            double BRP = (y - x - rx) / denom;

            if (gamepad1.right_trigger > 0.1) {
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

        if(Math.abs(gamepad2.right_stick_y) > 0.1){
            if(gamepad2.right_trigger > 0.1){
                fB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                fB.setPower(gamepad2.right_stick_y * .25);
            }else{
                fB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                fB.setPower(gamepad2.right_stick_y*.5);
            }
            curPos = fB.getCurrentPosition();
            telemetry.addData("4 bar tixks", fB.getCurrentPosition());
            telemetry.update();
        } else{
            fB.setTargetPosition(curPos);
            fB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fB.setPower(1);
            telemetry.addData("4 bar tixks", fB.getCurrentPosition());
            telemetry.update();
        }
        if (gamepad2.right_bumper) {
            if (isOpen) {
                clamp.setPosition(0.5);
            } else {
                clamp.setPosition(0);
            }
            isOpen = !isOpen;
            telemetry.addData("isOpen", isOpen);
            telemetry.update();
        }
        if(Math.abs(gamepad2.left_stick_y) > 0.1) {
            if (gamepad2.left_trigger > .2) {
                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setPower(gamepad2.left_stick_y * .375);
            } else {
                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setPower(gamepad2.left_stick_y*.75);
            }
        } else {
            lift.setTargetPosition(lift.getCurrentPosition());
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(.5);
        }
        telemetry.addData("4 bar tixks", fB.getCurrentPosition());
        telemetry.update();
    }
}
