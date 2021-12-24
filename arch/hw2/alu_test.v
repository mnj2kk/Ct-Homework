`include "alu.v"

module alu_test;
    reg [31:0] a, b;
    reg [2:0] alucontrol;
    
    wire [31:0] sum;
    wire zero;

  alu adder(a, b, alucontrol, sum, zero);

    initial begin
        a = 32'b0111110; b = 32'b1000;
        $monitor("%3b %32b", alucontrol, sum);
        #0 assign alucontrol = 3'b000;
        #1 assign alucontrol = 3'b001;
        #2 assign alucontrol = 3'b010;
        #3 assign alucontrol = 3'b011;
        #4 assign alucontrol = 3'b100;
        #5 assign alucontrol = 3'b101;
        #6 assign alucontrol = 3'b110;
        #7 assign alucontrol = 3'b111;
        
    end

endmodule