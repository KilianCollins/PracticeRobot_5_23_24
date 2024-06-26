package org.firstinspires.ftc.teamcode.Auto;

/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This OpMode illustrates the concept of driving a path based on time.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: RobotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backward for 1 Second
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name="RedsideAuto", group="Robot")

public class RedSideParkingAuto extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor rightFrontDrive = null; // hee hee hee haa change later

    private DcMotor rightBackDrive = null;

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;

    private DcMotor arm_motor = null;
    private DcMotor wrist_motor = null;
    private DcMotor outtake_intake_motor = null;
    private ElapsedTime runtime = new ElapsedTime();


    static final double FORWARD_SPEED = 0.4;
    //    static final double     TURN_SPEED    = 0.2;
    static final double REVERSE_SPEED = -0.4;

    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive ");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        arm_motor = hardwareMap.get(DcMotor.class, "arm_motor");
        wrist_motor = hardwareMap.get(DcMotor.class, "wrist_motor");
        outtake_intake_motor = hardwareMap.get(DcMotor.class,"intake");


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flip
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        outtake_intake_motor.setDirection(DcMotor.Direction.REVERSE);
        arm_motor.setDirection(DcMotor.Direction.REVERSE);
        wrist_motor.setDirection(DcMotor.Direction.FORWARD);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way


        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();

            //Step 1:Strafe Left
            leftFrontDrive.setPower(FORWARD_SPEED);
            rightFrontDrive.setPower(REVERSE_SPEED);
            leftBackDrive.setPower(REVERSE_SPEED);
            rightBackDrive.setPower(FORWARD_SPEED);
            sleep(1000);

            // Step 2:Go Forward`
            leftFrontDrive.setPower(REVERSE_SPEED);
            rightFrontDrive.setPower(REVERSE_SPEED);
            leftBackDrive.setPower(REVERSE_SPEED);
            rightBackDrive.setPower(REVERSE_SPEED);
            sleep(1000);

            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            leftFrontDrive.setPower(0);
            rightBackDrive.setPower(0);

            //Step 3: Arm Up

            arm_motor.setTargetPosition(3350);
            arm_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm_motor.setPower(0.6);
            sleep(2500);//1450 just


            //Step 4: Wrist to scoring position

            wrist_motor.setTargetPosition(350);
            wrist_motor.setMode((DcMotor.RunMode.RUN_TO_POSITION));
            wrist_motor.setPower(0.6);
            sleep(2000);

            //Step 5: outtake and intake drop pixels
            outtake_intake_motor.setPower(REVERSE_SPEED);
            sleep(8000);
//          Step 4: arm and wrist go back to resting position
            arm_motor.setTargetPosition(100);
            arm_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm_motor.setPower(-0.6);
            sleep(2500);//1450 just

            wrist_motor.setTargetPosition(0);
            wrist_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            wrist_motor.setPower(-0.6);
            sleep(2000);





//        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
//            telemetry.addData("Path", "Leg 2: %4.1f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//
//        // Step 3:  Drive Backward for 1 Second
//        leftBackDrive.setPower(-FORWARD_SPEED);
//        rightFrontDrive.setPower(-FORWARD_SPEED);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
//            telemetry.addData("Path", "Leg 3: %4.1f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }

//        // Step 4:  Stop
//        leftFrontDrive.setPower(0);
//        rightBackDrive.setPower(0);
//
//        telemetry.addData("Path", "Complete");
//        telemetry.update();
//        sleep(1000);
        }
    }

}