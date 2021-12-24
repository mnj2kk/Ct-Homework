module register_file(clk, we3, a1, a2, a3, wd3, rd1, rd2);
  input clk, we3;
  input [4:0] a1, a2, a3;
  input [31:0] wd3;
  output [31:0] rd1, rd2;
  reg[31:0] register; 
  assign rd1 = register[a1];
  assign rd2 = register[a2];
  always @(posedge clk) begin
    if(we3)begin
        register[a3] <= wd3;;
    end
  end
endmodule
