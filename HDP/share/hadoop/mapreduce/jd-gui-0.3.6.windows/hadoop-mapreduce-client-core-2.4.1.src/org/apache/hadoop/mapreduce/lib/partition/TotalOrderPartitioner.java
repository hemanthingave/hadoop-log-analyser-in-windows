/*   1:    */ package org.apache.hadoop.mapreduce.lib.partition;
/*   2:    */ 
/*   3:    */ import java.io.Closeable;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.lang.reflect.Array;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  11:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  12:    */ import org.apache.hadoop.conf.Configurable;
/*  13:    */ import org.apache.hadoop.conf.Configuration;
/*  14:    */ import org.apache.hadoop.fs.FileSystem;
/*  15:    */ import org.apache.hadoop.fs.Path;
/*  16:    */ import org.apache.hadoop.io.BinaryComparable;
/*  17:    */ import org.apache.hadoop.io.IOUtils;
/*  18:    */ import org.apache.hadoop.io.NullWritable;
/*  19:    */ import org.apache.hadoop.io.RawComparator;
/*  20:    */ import org.apache.hadoop.io.SequenceFile.Reader;
/*  21:    */ import org.apache.hadoop.io.WritableComparable;
/*  22:    */ import org.apache.hadoop.mapreduce.Job;
/*  23:    */ import org.apache.hadoop.mapreduce.Partitioner;
/*  24:    */ import org.apache.hadoop.util.ReflectionUtils;
/*  25:    */ 
/*  26:    */ @InterfaceAudience.Public
/*  27:    */ @InterfaceStability.Stable
/*  28:    */ public class TotalOrderPartitioner<K extends WritableComparable<?>, V>
/*  29:    */   extends Partitioner<K, V>
/*  30:    */   implements Configurable
/*  31:    */ {
/*  32:    */   private Node partitions;
/*  33:    */   public static final String DEFAULT_PATH = "_partition.lst";
/*  34:    */   public static final String PARTITIONER_PATH = "mapreduce.totalorderpartitioner.path";
/*  35:    */   public static final String MAX_TRIE_DEPTH = "mapreduce.totalorderpartitioner.trie.maxdepth";
/*  36:    */   public static final String NATURAL_ORDER = "mapreduce.totalorderpartitioner.naturalorder";
/*  37:    */   Configuration conf;
/*  38: 62 */   private static final Log LOG = LogFactory.getLog(TotalOrderPartitioner.class);
/*  39:    */   
/*  40:    */   public void setConf(Configuration conf)
/*  41:    */   {
/*  42:    */     try
/*  43:    */     {
/*  44: 79 */       this.conf = conf;
/*  45: 80 */       String parts = getPartitionFile(conf);
/*  46: 81 */       Path partFile = new Path(parts);
/*  47: 82 */       FileSystem fs = "_partition.lst".equals(parts) ? FileSystem.getLocal(conf) : partFile.getFileSystem(conf);
/*  48:    */       
/*  49:    */ 
/*  50:    */ 
/*  51: 86 */       Job job = new Job(conf);
/*  52: 87 */       Class<K> keyClass = job.getMapOutputKeyClass();
/*  53: 88 */       K[] splitPoints = readPartitions(fs, partFile, keyClass, conf);
/*  54: 89 */       if (splitPoints.length != job.getNumReduceTasks() - 1) {
/*  55: 90 */         throw new IOException("Wrong number of partitions in keyset");
/*  56:    */       }
/*  57: 92 */       RawComparator<K> comparator = job.getSortComparator();
/*  58: 94 */       for (int i = 0; i < splitPoints.length - 1; i++) {
/*  59: 95 */         if (comparator.compare(splitPoints[i], splitPoints[(i + 1)]) >= 0) {
/*  60: 96 */           throw new IOException("Split points are out of order");
/*  61:    */         }
/*  62:    */       }
/*  63: 99 */       boolean natOrder = conf.getBoolean("mapreduce.totalorderpartitioner.naturalorder", true);
/*  64:101 */       if ((natOrder) && (BinaryComparable.class.isAssignableFrom(keyClass))) {
/*  65:102 */         this.partitions = buildTrie((BinaryComparable[])splitPoints, 0, splitPoints.length, new byte[0], conf.getInt("mapreduce.totalorderpartitioner.trie.maxdepth", 200));
/*  66:    */       } else {
/*  67:113 */         this.partitions = new BinarySearchNode(splitPoints, comparator);
/*  68:    */       }
/*  69:    */     }
/*  70:    */     catch (IOException e)
/*  71:    */     {
/*  72:116 */       throw new IllegalArgumentException("Can't read partitions file", e);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Configuration getConf()
/*  77:    */   {
/*  78:121 */     return this.conf;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getPartition(K key, V value, int numPartitions)
/*  82:    */   {
/*  83:127 */     return this.partitions.findPartition(key);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static void setPartitionFile(Configuration conf, Path p)
/*  87:    */   {
/*  88:136 */     conf.set("mapreduce.totalorderpartitioner.path", p.toString());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String getPartitionFile(Configuration conf)
/*  92:    */   {
/*  93:144 */     return conf.get("mapreduce.totalorderpartitioner.path", "_partition.lst");
/*  94:    */   }
/*  95:    */   
/*  96:    */   static abstract interface Node<T>
/*  97:    */   {
/*  98:    */     public abstract int findPartition(T paramT);
/*  99:    */   }
/* 100:    */   
/* 101:    */   static abstract class TrieNode
/* 102:    */     implements TotalOrderPartitioner.Node<BinaryComparable>
/* 103:    */   {
/* 104:    */     private final int level;
/* 105:    */     
/* 106:    */     TrieNode(int level)
/* 107:    */     {
/* 108:166 */       this.level = level;
/* 109:    */     }
/* 110:    */     
/* 111:    */     int getLevel()
/* 112:    */     {
/* 113:169 */       return this.level;
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   class BinarySearchNode
/* 118:    */     implements TotalOrderPartitioner.Node<K>
/* 119:    */   {
/* 120:    */     private final K[] splitPoints;
/* 121:    */     private final RawComparator<K> comparator;
/* 122:    */     
/* 123:    */     BinarySearchNode(RawComparator<K> splitPoints)
/* 124:    */     {
/* 125:182 */       this.splitPoints = splitPoints;
/* 126:183 */       this.comparator = comparator;
/* 127:    */     }
/* 128:    */     
/* 129:    */     public int findPartition(K key)
/* 130:    */     {
/* 131:186 */       int pos = Arrays.binarySearch(this.splitPoints, key, this.comparator) + 1;
/* 132:187 */       return pos < 0 ? -pos : pos;
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   class InnerTrieNode
/* 137:    */     extends TotalOrderPartitioner.TrieNode
/* 138:    */   {
/* 139:196 */     private TotalOrderPartitioner.TrieNode[] child = new TotalOrderPartitioner.TrieNode[256];
/* 140:    */     
/* 141:    */     InnerTrieNode(int level)
/* 142:    */     {
/* 143:199 */       super();
/* 144:    */     }
/* 145:    */     
/* 146:    */     public int findPartition(BinaryComparable key)
/* 147:    */     {
/* 148:202 */       int level = getLevel();
/* 149:203 */       if (key.getLength() <= level) {
/* 150:204 */         return this.child[0].findPartition(key);
/* 151:    */       }
/* 152:206 */       return this.child[(0xFF & key.getBytes()[level])].findPartition(key);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private TrieNode LeafTrieNodeFactory(int level, BinaryComparable[] splitPoints, int lower, int upper)
/* 157:    */   {
/* 158:225 */     switch (upper - lower)
/* 159:    */     {
/* 160:    */     case 0: 
/* 161:227 */       return new UnsplitTrieNode(level, lower);
/* 162:    */     case 1: 
/* 163:230 */       return new SinglySplitTrieNode(level, splitPoints, lower);
/* 164:    */     }
/* 165:233 */     return new LeafTrieNode(level, splitPoints, lower, upper);
/* 166:    */   }
/* 167:    */   
/* 168:    */   private class LeafTrieNode
/* 169:    */     extends TotalOrderPartitioner.TrieNode
/* 170:    */   {
/* 171:    */     final int lower;
/* 172:    */     final int upper;
/* 173:    */     final BinaryComparable[] splitPoints;
/* 174:    */     
/* 175:    */     LeafTrieNode(int level, BinaryComparable[] splitPoints, int lower, int upper)
/* 176:    */     {
/* 177:249 */       super();
/* 178:250 */       this.lower = lower;
/* 179:251 */       this.upper = upper;
/* 180:252 */       this.splitPoints = splitPoints;
/* 181:    */     }
/* 182:    */     
/* 183:    */     public int findPartition(BinaryComparable key)
/* 184:    */     {
/* 185:255 */       int pos = Arrays.binarySearch(this.splitPoints, this.lower, this.upper, key) + 1;
/* 186:256 */       return pos < 0 ? -pos : pos;
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   private class UnsplitTrieNode
/* 191:    */     extends TotalOrderPartitioner.TrieNode
/* 192:    */   {
/* 193:    */     final int result;
/* 194:    */     
/* 195:    */     UnsplitTrieNode(int level, int value)
/* 196:    */     {
/* 197:264 */       super();
/* 198:265 */       this.result = value;
/* 199:    */     }
/* 200:    */     
/* 201:    */     public int findPartition(BinaryComparable key)
/* 202:    */     {
/* 203:269 */       return this.result;
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   private class SinglySplitTrieNode
/* 208:    */     extends TotalOrderPartitioner.TrieNode
/* 209:    */   {
/* 210:    */     final int lower;
/* 211:    */     final BinaryComparable mySplitPoint;
/* 212:    */     
/* 213:    */     SinglySplitTrieNode(int level, BinaryComparable[] splitPoints, int lower)
/* 214:    */     {
/* 215:278 */       super();
/* 216:279 */       this.lower = lower;
/* 217:280 */       this.mySplitPoint = splitPoints[lower];
/* 218:    */     }
/* 219:    */     
/* 220:    */     public int findPartition(BinaryComparable key)
/* 221:    */     {
/* 222:284 */       return this.lower + (key.compareTo(this.mySplitPoint) < 0 ? 0 : 1);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   private K[] readPartitions(FileSystem fs, Path p, Class<K> keyClass, Configuration conf)
/* 227:    */     throws IOException
/* 228:    */   {
/* 229:301 */     SequenceFile.Reader reader = new SequenceFile.Reader(fs, p, conf);
/* 230:302 */     ArrayList<K> parts = new ArrayList();
/* 231:303 */     K key = (WritableComparable)ReflectionUtils.newInstance(keyClass, conf);
/* 232:304 */     NullWritable value = NullWritable.get();
/* 233:    */     try
/* 234:    */     {
/* 235:306 */       while (reader.next(key, value))
/* 236:    */       {
/* 237:307 */         parts.add(key);
/* 238:308 */         key = (WritableComparable)ReflectionUtils.newInstance(keyClass, conf);
/* 239:    */       }
/* 240:310 */       reader.close();
/* 241:311 */       reader = null;
/* 242:    */     }
/* 243:    */     finally
/* 244:    */     {
/* 245:313 */       IOUtils.cleanup(LOG, new Closeable[] { reader });
/* 246:    */     }
/* 247:315 */     return (WritableComparable[])parts.toArray((WritableComparable[])Array.newInstance(keyClass, parts.size()));
/* 248:    */   }
/* 249:    */   
/* 250:    */   private class CarriedTrieNodeRef
/* 251:    */   {
/* 252:    */     TotalOrderPartitioner.TrieNode content;
/* 253:    */     
/* 254:    */     CarriedTrieNodeRef()
/* 255:    */     {
/* 256:331 */       this.content = null;
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   private TrieNode buildTrie(BinaryComparable[] splits, int lower, int upper, byte[] prefix, int maxDepth)
/* 261:    */   {
/* 262:348 */     return buildTrieRec(splits, lower, upper, prefix, maxDepth, new CarriedTrieNodeRef());
/* 263:    */   }
/* 264:    */   
/* 265:    */   private TrieNode buildTrieRec(BinaryComparable[] splits, int lower, int upper, byte[] prefix, int maxDepth, TotalOrderPartitioner<K, V>.CarriedTrieNodeRef ref)
/* 266:    */   {
/* 267:374 */     int depth = prefix.length;
/* 268:377 */     if ((depth >= maxDepth) || (lower >= upper - 1))
/* 269:    */     {
/* 270:380 */       if ((lower == upper) && (ref.content != null)) {
/* 271:381 */         return ref.content;
/* 272:    */       }
/* 273:383 */       TrieNode result = LeafTrieNodeFactory(depth, splits, lower, upper);
/* 274:384 */       ref.content = (lower == upper ? result : null);
/* 275:385 */       return result;
/* 276:    */     }
/* 277:387 */     TotalOrderPartitioner<K, V>.InnerTrieNode result = new InnerTrieNode(depth);
/* 278:388 */     byte[] trial = Arrays.copyOf(prefix, prefix.length + 1);
/* 279:    */     
/* 280:390 */     int currentBound = lower;
/* 281:391 */     for (int ch = 0; ch < 255; ch++)
/* 282:    */     {
/* 283:392 */       trial[depth] = ((byte)(ch + 1));
/* 284:393 */       lower = currentBound;
/* 285:394 */       while ((currentBound < upper) && 
/* 286:395 */         (splits[currentBound].compareTo(trial, 0, trial.length) < 0)) {
/* 287:398 */         currentBound++;
/* 288:    */       }
/* 289:400 */       trial[depth] = ((byte)ch);
/* 290:401 */       result.child[(0xFF & ch)] = buildTrieRec(splits, lower, currentBound, trial, maxDepth, ref);
/* 291:    */     }
/* 292:405 */     trial[depth] = -1;
/* 293:406 */     result.child['ÿ'] = buildTrieRec(splits, lower, currentBound, trial, maxDepth, ref);
/* 294:    */     
/* 295:    */ 
/* 296:409 */     return result;
/* 297:    */   }
/* 298:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner
 * JD-Core Version:    0.7.0.1
 */