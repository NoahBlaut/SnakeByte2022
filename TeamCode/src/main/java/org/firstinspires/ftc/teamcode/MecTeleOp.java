package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.utils.TeleLib;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class MecTeleOp extends OpMode {
    TeleLib robot;

    @Override
    public void init() {
        robot = new TeleLib(this);
    }

    @Override
    public void loop() {
        robot.drivetrain(this);
        robot.mechanism(this);
        try {
            robot.claw(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        robot.kill();
    }
}



