module control_unit(opcode, funct, memtoreg, memwrite, branch, alusrc, regdst, regwrite, alucontrol);
  input [5:0] opcode, funct;
  output reg memtoreg, memwrite, branch, alusrc, regdst, regwrite;
  output reg [2:0] alucontrol;
  always @(opcode,funct) begin
    begin
      case(opcode)
        //lw
        6'b100011:
          begin
            branch <= 0;
            regwrite <= 1;
            regdst <= 0;
            alusrc <= 1;
            memwrite <= 0;
            memtoreg <= 1;
            alucontrol <= 3'b010;
          end
        //sw
        6'b101011:
          begin
            branch <= 0;
            regwrite <= 0;
            regdst <= 0;
            alusrc <= 1;
            memtoreg <= 0;
            memwrite <= 1;
            alucontrol <= 3'b010;
          end
        //beq
        6'b000100:
          begin
            branch <= 1;
            regwrite <= 0;
            regdst <= 0;
            alusrc <= 0;
            memtoreg <= 0;
            memwrite <= 0;
            alucontrol <= 3'b110;

          end
        //addi
        6'b001000:
          begin
            branch <= 0;
            regwrite <= 1;
            regdst <= 0;
            alusrc <= 1;
            alucontrol <= 3'b010;
            memtoreg <= 0;
            memwrite <= 0;
          end
        //other cases
        6'b000000:
          begin
            branch <= 0;
            regdst <= 1;
            alusrc <= 0;
            memwrite <= 0;
            memtoreg <= 0;
            regwrite <= 1;
            begin
              case(funct)
                //add
                6'b100000:
                  alucontrol <= 3'b010;
                //sub
                6'b100010:
                  alucontrol <= 3'b110;
                //and
                6'b100100:
                  alucontrol <= 3'b000;
                //or
                6'b100101:
                  alucontrol <= 3'b001;
                //slt
                6'b101010:
                  alucontrol <= 3'b111;
              endcase
            end
          end
      endcase
    end
  end
endmodule
