module data_memory(a, we, clk, wd, rd);
  input we, clk;
  input [31:0] a;
  input [31:0] wd;
  output [31:0] rd;
  reg[31:0] ram [0:300000000];
  always @(posedge clk) begin
    if(we==1) begin
      ram[a/4] = wd;
    end
    
  end
  assign rd = ram[a/4];
endmodule

module instruction_memory(a, rd);
  input [31:0] a;
  output [31:0] rd;

  // Note that at this point our programs cannot be larger then 64 subsequent commands.
  // Increase constant below if larger programs are going to be executed.
  reg [31:0]  ram[0:300000000];

  initial begin
      $readmemb("instructions.dat", ram);
    end
  assign rd = ram[a/4];
endmodule

