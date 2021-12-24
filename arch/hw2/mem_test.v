`include "alu.v"
`include "memory.v"


module alu_test;
    reg [31:0] addr;
    wire [31:0] out, dout;
    reg clk, we;

    instruction_memory mem(addr, out);
    data_memory dmem(addr , we, clk, out, dout);
    // instruction_memory mem = new instruction_memory(addr, out)

    initial begin
        clk = 0;
        $monitor("%d %32b %1b", addr, out, clk);
        we = 1;
        #0 assign addr = 0;
        #1 assign addr = 4;
        #2 assign addr = 8;
        #3 assign addr = 12;
        #4 assign addr = 16;
        #5 assign addr = 20;
        #6 assign addr = 24;
        #7 assign addr = 28;
        we = 0;
        #8
        #9 assign addr = 0;
        $display("- - - - - - - - -  --  --  - - -");
        $monitor("Read: %d %32b", addr, out);
        
        #10 assign addr = 4;
        #11 assign addr = 8;
        #12 assign addr = 12;
        #13 assign addr = 16;
        #14 assign addr = 20;
        #15 assign addr = 24;
        #16 assign addr = 28;
        $finish();
    end
    
    always begin
        #1 clk = ~clk;
    end

    

endmodule