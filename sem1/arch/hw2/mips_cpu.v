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
  //There started
  wire memtoreg, branch, alu_src, regdst,zero;
  wire[2:0] alucontrol;
  control_unit control(instruction_memory_rd[31:26], instruction_memory_rd[5:0],
                    memtoreg, data_memory_we, branch, alu_src, regdst, register_we3, alucontrol);

  assign register_a1 = instruction_memory_rd[25:21];
  assign register_a2 = instruction_memory_rd[20:16];
  mux2_5 reg_dst_mux(instruction_memory_rd[20:16], instruction_memory_rd[15:11], regdst, register_a3);

  //alu shit
  wire[31:0]  srcb, aluresult, signimm,result;
  sign_extend sign_extender1(instruction_memory_rd[15:0], signimm);
  mux2_32 srcb_mux(register_rd2, signimm, alu_src, srcb);
  alu eh(register_rd1, srcb, alucontrol, aluresult, zero);
  mux2_32 result_mux(aluresult, data_memory_rd, memtoreg, result);

  //beq
  wire[31:0] sign_out;
  shl_2 sign_extender2(signimm, sign_out);
  wire[31:0] pc_branch;
  adder branch_add(sign_out, pc_add, pc_branch);
  //next command
  wire pc_src;
  wire[31:0] pc_add,pc_out;
  adder plus_adder(instruction_memory_a, 32'b00000000000000000000000000000100, pc_add);
  assign pc_src = branch & zero;
  mux2_32 pc_mux(pc_add, pc_branch, pc_src, pc_out);
  d_flop big_floppa_russian_cat(pc_out, clk, instruction_memory_a);
  assign register_wd3 = result;
  assign data_memory_wd = register_rd2;
  assign data_memory_a =  aluresult;

endmodule
