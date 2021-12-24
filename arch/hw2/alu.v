module alu(srca, srcb, alucontrol, aluresult, zero);
  input [31:0] srca, srcb;
  input [2:0] alucontrol;
  output reg  [31:0] aluresult;
  output reg  zero;
  always @(alucontrol,srca,srcb)
  begin
    case (alucontrol)
      3'b010:
        //add
        aluresult <= srca + srcb;
      3'b000:
        //and
        aluresult <= srca & srcb;
      3'b001:
        //or
        aluresult <= srca | srcb;
      3'b111:
        //slt
        aluresult <= srca < srcb;
      3'b110:
        //sub
        aluresult <= srca + (~srcb) + 1;
      3'b101:
        aluresult <= srca | (~srcb);
      3'b100:
        aluresult <= srca & (~srcb);
          
      default:
        aluresult <= 0;
    endcase
  end
  always @(aluresult) 
  begin
    if (aluresult==0)begin
      zero <= 1'b1;
    end else begin
      zero <= 1'b0;
    end
  end
endmodule
