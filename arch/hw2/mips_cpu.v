`include "alu.v"
`include "control_unit.v"
`include "util.v"
module mips_cpu(clk, instruction_memory_a, instruction_memory_rd, data_memory_a, data_memory_rd, data_memory_we, data_memory_wd,
                register_a1, register_a2, register_a3, register_we3, register_wd3, register_rd1, register_rd2);
  input clk;
  output data_memory_we;
  output [31:0] instruction_memory_a, data_memory_a, data_memory_wd;
  inout [31:0] instruction_memory_rd, data_memory_rd;
  output register_we3;
  output [4:0] register_a1, register_a2, register_a3;
  output [31:0] register_wd3;
  inout [31:0] register_rd1, register_rd2;

  wire[31:0] sign_imm,alu_result,src_b;
  wire zero;
  wire [2:0] alu_control;
  wire mem_to_reg,alu_src,reg_dst,mem_write,reg_write;
  assign register_a1 = instruction_memory_rd[25:21];
  assign register_a2 = instruction_memory_rd[20:16];
  assign data_memory_wd = register_rd2;
  //alu shit
  mux2_32 alu_src_mul(register_rd2,sign_imm,alu_src,src_b);
  mux2_5 alu_reg_dst_mul(instruction_memory_rd[20:16],instruction_memory_rd[15:11],reg_dst,register_a3);
  sign_extend sign_ext(instruction_memory_rd[15:0],sign_imm);
  alu proc_alu(register_rd1,src_b,alu_control,alu_result,zero);
  
  mux2_32 mem_to_reg_mul(data_memory_rd,alu_result,mem_to_reg,register_wd3);
  //beq
  wire branch_c;
  wire[31:0] sign_add,add_mux,mux_flop;
  and beq_and(zero,branch,branch_c);
  shl_2 shl(sign_imm,sign_add);
  adder beq_adder(add_mux,sign_add,);
  mux2_32 beq_mux(add1_mux,add_mux,branch_c,mux_flop);
  //control unit
  assign regiter_we3 = reg_write;
  control_unit control(instruction_memory_rd[31:26],instruction_memory_rd[5:0],mem_to_reg,mem_write,branch,alu_src,reg_dst,reg_write,alu_control);
  //next command  
  wire[31:0] add1_mux,flop_memory,flop_add;
  d_flop flop(mux_flop,clk,instruction_memory_a);
  adder add(flop_add,32'b00000000000000000000000000000100,add_mux);
endmodule
