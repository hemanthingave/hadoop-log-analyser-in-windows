/*   1:    */ package org.apache.hadoop.mapreduce.lib.input;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.net.URI;
/*   7:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*   8:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*   9:    */ import org.apache.hadoop.fs.Path;
/*  10:    */ import org.apache.hadoop.io.Text;
/*  11:    */ import org.apache.hadoop.io.Writable;
/*  12:    */ import org.apache.hadoop.mapreduce.InputSplit;
/*  13:    */ 
/*  14:    */ @InterfaceAudience.Public
/*  15:    */ @InterfaceStability.Stable
/*  16:    */ public class CombineFileSplit
/*  17:    */   extends InputSplit
/*  18:    */   implements Writable
/*  19:    */ {
/*  20:    */   private Path[] paths;
/*  21:    */   private long[] startoffset;
/*  22:    */   private long[] lengths;
/*  23:    */   private String[] locations;
/*  24:    */   private long totLength;
/*  25:    */   
/*  26:    */   public CombineFileSplit() {}
/*  27:    */   
/*  28:    */   public CombineFileSplit(Path[] files, long[] start, long[] lengths, String[] locations)
/*  29:    */   {
/*  30: 62 */     initSplit(files, start, lengths, locations);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CombineFileSplit(Path[] files, long[] lengths)
/*  34:    */   {
/*  35: 66 */     long[] startoffset = new long[files.length];
/*  36: 67 */     for (int i = 0; i < startoffset.length; i++) {
/*  37: 68 */       startoffset[i] = 0L;
/*  38:    */     }
/*  39: 70 */     String[] locations = new String[files.length];
/*  40: 71 */     for (int i = 0; i < locations.length; i++) {
/*  41: 72 */       locations[i] = "";
/*  42:    */     }
/*  43: 74 */     initSplit(files, startoffset, lengths, locations);
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void initSplit(Path[] files, long[] start, long[] lengths, String[] locations)
/*  47:    */   {
/*  48: 79 */     this.startoffset = start;
/*  49: 80 */     this.lengths = lengths;
/*  50: 81 */     this.paths = files;
/*  51: 82 */     this.totLength = 0L;
/*  52: 83 */     this.locations = locations;
/*  53: 84 */     for (long length : lengths) {
/*  54: 85 */       this.totLength += length;
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public CombineFileSplit(CombineFileSplit old)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 93 */     this(old.getPaths(), old.getStartOffsets(), old.getLengths(), old.getLocations());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public long getLength()
/*  65:    */   {
/*  66: 98 */     return this.totLength;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public long[] getStartOffsets()
/*  70:    */   {
/*  71:103 */     return this.startoffset;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public long[] getLengths()
/*  75:    */   {
/*  76:108 */     return this.lengths;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public long getOffset(int i)
/*  80:    */   {
/*  81:113 */     return this.startoffset[i];
/*  82:    */   }
/*  83:    */   
/*  84:    */   public long getLength(int i)
/*  85:    */   {
/*  86:118 */     return this.lengths[i];
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getNumPaths()
/*  90:    */   {
/*  91:123 */     return this.paths.length;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Path getPath(int i)
/*  95:    */   {
/*  96:128 */     return this.paths[i];
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Path[] getPaths()
/* 100:    */   {
/* 101:133 */     return this.paths;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String[] getLocations()
/* 105:    */     throws IOException
/* 106:    */   {
/* 107:138 */     return this.locations;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void readFields(DataInput in)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:142 */     this.totLength = in.readLong();
/* 114:143 */     int arrLength = in.readInt();
/* 115:144 */     this.lengths = new long[arrLength];
/* 116:145 */     for (int i = 0; i < arrLength; i++) {
/* 117:146 */       this.lengths[i] = in.readLong();
/* 118:    */     }
/* 119:148 */     int filesLength = in.readInt();
/* 120:149 */     this.paths = new Path[filesLength];
/* 121:150 */     for (int i = 0; i < filesLength; i++) {
/* 122:151 */       this.paths[i] = new Path(Text.readString(in));
/* 123:    */     }
/* 124:153 */     arrLength = in.readInt();
/* 125:154 */     this.startoffset = new long[arrLength];
/* 126:155 */     for (int i = 0; i < arrLength; i++) {
/* 127:156 */       this.startoffset[i] = in.readLong();
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void write(DataOutput out)
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:161 */     out.writeLong(this.totLength);
/* 135:162 */     out.writeInt(this.lengths.length);
/* 136:163 */     for (long length : this.lengths) {
/* 137:164 */       out.writeLong(length);
/* 138:    */     }
/* 139:166 */     out.writeInt(this.paths.length);
/* 140:167 */     for (Path p : this.paths) {
/* 141:168 */       Text.writeString(out, p.toString());
/* 142:    */     }
/* 143:170 */     out.writeInt(this.startoffset.length);
/* 144:171 */     for (long length : this.startoffset) {
/* 145:172 */       out.writeLong(length);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String toString()
/* 150:    */   {
/* 151:178 */     StringBuffer sb = new StringBuffer();
/* 152:179 */     for (int i = 0; i < this.paths.length; i++)
/* 153:    */     {
/* 154:180 */       if (i == 0) {
/* 155:181 */         sb.append("Paths:");
/* 156:    */       }
/* 157:183 */       sb.append(this.paths[i].toUri().getPath() + ":" + this.startoffset[i] + "+" + this.lengths[i]);
/* 158:185 */       if (i < this.paths.length - 1) {
/* 159:186 */         sb.append(",");
/* 160:    */       }
/* 161:    */     }
/* 162:189 */     if (this.locations != null)
/* 163:    */     {
/* 164:190 */       String locs = "";
/* 165:191 */       StringBuffer locsb = new StringBuffer();
/* 166:192 */       for (int i = 0; i < this.locations.length; i++) {
/* 167:193 */         locsb.append(this.locations[i] + ":");
/* 168:    */       }
/* 169:195 */       locs = locsb.toString();
/* 170:196 */       sb.append(" Locations:" + locs + "; ");
/* 171:    */     }
/* 172:198 */     return sb.toString();
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.input.CombineFileSplit
 * JD-Core Version:    0.7.0.1
 */